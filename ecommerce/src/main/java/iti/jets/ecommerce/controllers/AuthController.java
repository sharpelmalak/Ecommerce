package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.config.CustomAuthenticationSuccessHandler;
import iti.jets.ecommerce.dto.CustomerDTO;
import iti.jets.ecommerce.dto.LoginDTO;
import iti.jets.ecommerce.services.CategoryService;
import iti.jets.ecommerce.services.CustomerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import iti.jets.ecommerce.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private CategoryService categoryService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JWTService jwtService;

    @PostMapping("/register")
    public String register(@ModelAttribute CustomerDTO customerDTO,Model model) {
        // Call the service to save the user
        boolean isRegistered = customerService.RegisterCustomer(customerDTO);
        
        if (isRegistered) {
            model.addAttribute("successMessage", "Registration successful! Please log in.");
            return "signup3"; // Return to signup form if registration fails            
        } else {
            model.addAttribute("errorMessage", "Registration failed. Due to Username or Email Exists");
            model.addAttribute("customerDTO", customerDTO); // Preserve the data
            model.addAttribute("categories", categoryService.getAllCategories());
            return "login"; // Return to signup form if registration fails
        }
    }
    

    @GetMapping("/login")
    public String login(@RequestParam(value = "error",required = false) String error, HttpServletRequest request) {
        return "login";
    }

    @GetMapping("/registeration-form")
    public String showRegisterationForm(Model  model) {
        CustomerDTO customerDTO = new CustomerDTO();
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("customerDTO", customerDTO);
        return "signup3";
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


    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsernameAvailability(@RequestParam("username") String username) {
        boolean isAvailable = customerService.isUsernameAvailable(username);
        return ResponseEntity.ok(isAvailable);
    }    
}