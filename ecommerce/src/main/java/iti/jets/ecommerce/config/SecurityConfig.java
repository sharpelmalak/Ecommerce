package iti.jets.ecommerce.config;

import iti.jets.ecommerce.config.filters.JwtFilter;
import iti.jets.ecommerce.config.handlers.CustomAuthenticationSuccessHandler;
import iti.jets.ecommerce.config.handlers.CustomLogoutHandler;
import iti.jets.ecommerce.services.CustomOAuth2UserService;
import iti.jets.ecommerce.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    private CustomLogoutHandler logoutHandler;


    @Autowired
    private CustomOAuth2UserService oAuth2UserService;


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests(auth -> auth
               // Allow access to Swagger UI and OpenAPI documentation without authentication
                        .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/swagger-resources/**",
                                        "/webjars/**",
                                        "/auth/**",
                                        "/shop/**",
                                        "/products/**",
                                        "/cart/**",
                                        "/category/**",
                                        "/css/**", "/js/**", "/img/**", "/fonts/**","/common/**",
                                        "/home",
                                        "/error",
                                        "/details/**",
                                        "/"
                                ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/checkout/**",
                                "/user/**",
                                "/promotions/**",
                                "/payment/**",
                                "/orders/**",
                                "/cards/**",
                                "/customers/**"
                                ).hasRole("CUSTOMER")
                                .anyRequest().authenticated()
                )
                .formLogin(form-> form
                        .loginPage("/auth/login")
                        .successHandler(successHandler)
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/auth/login") // Custom login page for OAuth2
                        .defaultSuccessUrl("/home")
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                        .permitAll() // Allow access to login page for OAuth2
                )
                .logout(logout-> logout
                        .logoutUrl("/logout") // Define the logout URL
                        .addLogoutHandler(logoutHandler) // Add custom logout handler
                        .logoutSuccessUrl("/home") // Redirect URL after logout
                        .permitAll())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            System.out.println("Blocked access to: " + request.getRequestURI());
                            System.out.println("Reason: " + authException.getMessage());
                            if( request.getRequestURI().equals("/checkout"))
                            response.sendRedirect("/auth/login");
                            else response.sendRedirect("/error");
                        })
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
