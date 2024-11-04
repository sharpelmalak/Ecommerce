package iti.jets.ecommerce.config.handlers;

import iti.jets.ecommerce.dto.CartItemDTO;
import iti.jets.ecommerce.services.CartService;
import iti.jets.ecommerce.services.JWTService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

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
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("CUSTOMER");
        String token = jwtService.generateToken(authentication.getName(),role);
        // Set the JWT token as an HTTP-only cookie
        Cookie jwtCookie = new Cookie("token", token);
        jwtCookie.setHttpOnly(false); // HTTP-only for security
        jwtCookie.setSecure(true); // Use true if your app is served over HTTPS
        jwtCookie.setPath("/"); // Set the path to allow access for your entire app
        jwtCookie.setMaxAge(7 * 24 * 60 * 60); // Optional: Set cookie expiration (7 days)
        response.addCookie(jwtCookie);
        // Retrieve the original URL from the session


        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            response.sendRedirect("/admin/products");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            handleCartForUser(authentication.getName(),request,response);
            String redirectUrl = (String) request.getSession().getAttribute("redirectAfterLogin");

            if (redirectUrl != null) {
                // Remove the URL from the session to prevent future redirects
                request.getSession().removeAttribute("redirectAfterLogin");
                // Redirect to the original requested URL
                response.sendRedirect(redirectUrl);
            }
            else response.sendRedirect("/home");
        } else {
            response.sendRedirect("/auth/login");  // Fallback URL if role not matched
        }
    }


    private void handleCartForUser(String username, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<CartItemDTO> cartItemDTOList = cartService.loadCart(username);
        if (cartItemDTOList != null && !cartItemDTOList.isEmpty()) {
            HttpSession session = request.getSession();
            List<CartItemDTO> clientCart = cartService.loadCartFromCookie(request.getCookies(),session);
            List<CartItemDTO> mergedCart = cartService.mergeCarts(cartItemDTOList,clientCart);
            session.setAttribute("cart", mergedCart);
            Cookie cookie = cartService.persistCartInCookie(session);
            //cartService.resetCart(username);
            cartService.saveCart(mergedCart,username);
            response.addCookie(cookie);
        }
    }
}

