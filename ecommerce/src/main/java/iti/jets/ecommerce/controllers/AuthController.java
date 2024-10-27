package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.dto.CustomerDTO;
import iti.jets.ecommerce.dto.LoginDTO;
import iti.jets.ecommerce.services.CustomerService;
import org.springframework.security.core.GrantedAuthority;
import iti.jets.ecommerce.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> register(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.RegisterCustomer(customerDTO));
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error",required = false) String error) {
        return "login";
    }

    @PostMapping(value = "/login", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(), loginDTO.getPassword()));

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(loginDTO.getUsername());
                String role = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .findFirst()
                        .orElse("CUSTOMER"); // Default to USER if no role is found

                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("role", role);
                return ResponseEntity.ok(response); // Return token and role as response
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Login Failed"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Login Failed"));
        }
    }
    
}