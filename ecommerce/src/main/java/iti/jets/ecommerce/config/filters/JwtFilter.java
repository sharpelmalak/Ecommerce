package iti.jets.ecommerce.config.filters;




import iti.jets.ecommerce.config.handlers.CustomAuthenticationSuccessHandler;
import iti.jets.ecommerce.services.CustomUserDetailsService;
import iti.jets.ecommerce.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ApplicationContext context;


    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        // Skip the filter for static resources
        if (uri.startsWith("/cart") || uri.startsWith("/css") || uri.startsWith("/js") || uri.startsWith("/img") || uri.startsWith("/fonts")) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("URL FROM JWT-FILTER : "+ uri);
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        // Check for existing authentication in the security context
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            // If authenticated, check if they are trying to access login or register pages
            if (request.getRequestURI().equals("/auth/login") || request.getRequestURI().equals("/auth/register")) {
                // Redirect if user is already authenticated
                System.out.println("user is already authenticated from context");
                successHandler.onAuthenticationSuccess(request,response,SecurityContextHolder.getContext().getAuthentication());
                return;
            }
        }

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            userName = jwtService.getUsernameFromJwtToken(token);
        }



        // If no token from header, check for cookies
        if (userName == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        token = cookie.getValue(); // Get the token from the cookie
                        userName = jwtService.getUsernameFromJwtToken(token);
                        if(userName == null){
                            Cookie coo = new Cookie("token", null);
                            coo.setPath("/"); // Set the path to match the cookie path
                            coo.setHttpOnly(true); // Ensure the cookie is only accessible via HTTP(S)
                            coo.setMaxAge(0); // Set cookie age to 0 to delete it
                            response.addCookie(coo);
                        }
                        break;
                    }
                }
            }
        }

        // check it's not null and auth
        if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUsername(userName);

            if(jwtService.validateJwtToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                // If the user is logging in and is already authenticated, call success handler
                if (request.getRequestURI().equals("/auth/login")|| request.getRequestURI().equals("/auth/register")) {
                    successHandler.onAuthenticationSuccess(request, response, authToken);
                    return;
                }
                System.out.println("Authenticated as: " + authToken.getPrincipal());
                System.out.println("Current authentication in context: " + SecurityContextHolder.getContext().getAuthentication());
            }
        }

        if (request.getRequestURI().equals("/checkout") && SecurityContextHolder.getContext().getAuthentication()==null) {
            // Redirect if user is already authenticated
            String requestUri = request.getRequestURI();
            // Store the original URL in the session
            request.getSession().setAttribute("redirectAfterLogin", requestUri);
        }
        System.out.println("final authentication in context: " + SecurityContextHolder.getContext().getAuthentication());
        filterChain.doFilter(request, response);
    }
}
