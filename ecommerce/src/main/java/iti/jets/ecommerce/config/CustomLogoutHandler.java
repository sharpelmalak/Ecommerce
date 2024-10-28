package iti.jets.ecommerce.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;


@Component
public class CustomLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {


        System.out.println("CustomLogoutHandler CALLED");
        // Clear the token cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/"); // Set the path to match the cookie path
        cookie.setHttpOnly(false); // Ensure the cookie is only accessible via HTTP(S)
        cookie.setMaxAge(0); // Set cookie age to 0 to delete it
        response.addCookie(cookie);

        // Additional logout logic (if any)
    }
}

