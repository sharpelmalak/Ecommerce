package iti.jets.ecommerce.config.handlers;

import iti.jets.ecommerce.dto.CartItemDTO;
import iti.jets.ecommerce.services.CartService;
import iti.jets.ecommerce.services.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CustomLogoutHandler implements LogoutHandler {

    private CartService cartService;
    private JWTService jwtService;

    @Autowired
    public CustomLogoutHandler(CartService cartService,JWTService jwtService) {
        this.cartService = cartService;
        this.jwtService = jwtService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {


//        System.out.println("CustomLogoutHandler CALLED");
//        String token = null;
//        String username = null;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("token".equals(cookie.getName())) {
//                    token = cookie.getValue(); // Get the token from the cookie
//                    username = jwtService.getUsernameFromJwtToken(token);
//                    break;
//                }
//            }
//        }

        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/"); // Set the path to match the cookie path
        cookie.setHttpOnly(true); // Ensure the cookie is only accessible via HTTP(S)
        cookie.setMaxAge(0); // Set cookie age to 0 to delete it
        response.addCookie(cookie);

//        if (username != null && ("ROLE_CUSTOMER").equals(jwtService.getRoleFromJwtToken(token))) {
//            handleCustomerCart(request,username);
//        }


    }

    private void handleCustomerCart(HttpServletRequest request,String username)
    {
        HttpSession session = request.getSession();
        List<CartItemDTO> cart;
        if (session != null && session.getAttribute("cart") != null) {
            cart = (List<CartItemDTO>) session.getAttribute("cart");
        }
        else {
            cart = cartService.loadCartFromCookie(request.getCookies(),session);
        }
        if(cart != null && !cart.isEmpty()) {
            cartService.saveCart(cart,username);
        }
    }
}

