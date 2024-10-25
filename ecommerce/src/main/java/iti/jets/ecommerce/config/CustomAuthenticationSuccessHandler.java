package iti.jets.ecommerce.config;

import iti.jets.ecommerce.models.Admin;
import iti.jets.ecommerce.models.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();

        if (principal instanceof Admin) {
            response.sendRedirect("/admin/home"); // Admin home page
        } else if (principal instanceof Customer) {
            response.sendRedirect("/customer/home"); // Customer home page
        } else {
            response.sendRedirect("/home"); // Default home
        }
    }
}

