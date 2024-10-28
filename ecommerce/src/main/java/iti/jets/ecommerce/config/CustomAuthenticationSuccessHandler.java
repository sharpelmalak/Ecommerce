package iti.jets.ecommerce.config;

import iti.jets.ecommerce.dto.CartItemDTO;
import iti.jets.ecommerce.models.Admin;
import iti.jets.ecommerce.models.Customer;
import iti.jets.ecommerce.services.CartService;
import iti.jets.ecommerce.services.JWTService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private JWTService jwtService;
    private CartService cartService;

    @Autowired
    public CustomAuthenticationSuccessHandler(JWTService jwtService, CartService cartService) {
        this.jwtService = jwtService;
        this.cartService = cartService;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        System.out.println("IN SUCCESS HANDLER" + request.getRequestURI());
        String token = jwtService.generateToken(authentication.getName());
        // Set the JWT token as an HTTP-only cookie
        Cookie jwtCookie = new Cookie("token", token);
        jwtCookie.setHttpOnly(false); // HTTP-only for security
        jwtCookie.setSecure(true); // Use true if your app is served over HTTPS
        jwtCookie.setPath("/"); // Set the path to allow access for your entire app
        jwtCookie.setMaxAge(7 * 24 * 60 * 60); // Optional: Set cookie expiration (7 days)

        // Add the cookie to the response
        response.addCookie(jwtCookie);
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            response.sendRedirect("/admin/products");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            List<CartItemDTO> cartItemDTOList = cartService.loadCart(authentication.getName());
            if (cartItemDTOList != null && !cartItemDTOList.isEmpty()) {
                HttpSession session = request.getSession();
                List<CartItemDTO> clientCart = cartService.loadCartFromCookie(request.getCookies(),session);
                List<CartItemDTO> mergedCart = cartService.mergeCarts(cartItemDTOList,clientCart);
                session.setAttribute("cart", mergedCart);
                Cookie cookie = cartService.persistCartInCookie(session);
                cartService.resetCart(authentication.getName());
                response.addCookie(cookie);
            }
            response.sendRedirect("/home");
        } else {
            response.sendRedirect("/default");  // Fallback URL if role not matched
        }
    }
}

