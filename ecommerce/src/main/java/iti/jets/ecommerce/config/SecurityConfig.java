package iti.jets.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/**").permitAll() // Allow access to authentication endpoints
//                       // .requestMatchers("/api/admin/**").hasRole("ADMIN") // Restrict access to admin endpoints
//                        .anyRequest().authenticated() // Other endpoints require authentication
//                )
//                .formLogin(); // Optionally, configure form login if needed

        return http.build();
    }
}
