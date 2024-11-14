package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.config.handlers.CustomAuthenticationSuccessHandler;
import iti.jets.ecommerce.dto.CustomerDTO;
import iti.jets.ecommerce.dto.LoginDTO;
import iti.jets.ecommerce.services.CategoryService;
import iti.jets.ecommerce.services.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomerService customerService; 
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;


    @Autowired
    private JWTService jwtService;

    @PostMapping("/register")
    public String register(@ModelAttribute CustomerDTO customerDTO, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Log the CustomerDTO
        System.out.println("Received Customer DTO: " + customerDTO);

        // Call the service to save the user
        boolean isRegistered = customerService.RegisterCustomer(customerDTO);
        
        if (isRegistered) {
            successHandler.onAuthenticationSuccess(request,response,authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            customerDTO.getUsername(), customerDTO.getPassword()))
            ); 
        } else {
            model.addAttribute("errorMessage", "Registration failed. Due to Username or Email Exists");
            model.addAttribute("customerDTO", customerDTO); // Preserve the data
            model.addAttribute("categories", categoryService.getAllCategories());
        }
        return "signup"; // Return to signup form if registration fails
    }
    

    @GetMapping("/login")
    public String login(@RequestParam(value = "error",required = false) String error, Model model) {
       if(error != null && error.equals("true"))
       {
           model.addAttribute("errorMessage", "Invalid credentials");
       }
        return "login";
    }

    @GetMapping("/registeration")
    public String showRegisterationForm(Model  model) {
        CustomerDTO customerDTO = new CustomerDTO();
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("customerDTO", customerDTO);
        return "signup";
    }



    /*  Not Used, Spring Boot Handles this part by this method :  
        successHandler.onAuthenticationSuccess(request,response,authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(customerDTO.getUsername(), customerDTO.getPassword())); */
    /* 
    @PostMapping(value = "/login", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(), loginDTO.getPassword()));

            if (authentication.isAuthenticated()) {
                String role = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .findFirst()
                        .orElse("CUSTOMER"); // Default to USER if no role is found

                String token = jwtService.generateToken(loginDTO.getUsername(),role);
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
    }*/


    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsernameAvailability(@RequestParam("username") String username) {
        System.out.println("Checking availability for username: " + username);
        boolean isAvailable = customerService.isUsernameAvailable(username);
        return ResponseEntity.ok(isAvailable);
    }


    @GetMapping("/status")
    public ResponseEntity<?> checkAuthenticationStatus(Principal principal) {
        if (principal != null) {
            // User is authenticated
            Map<String, Object> response = new HashMap<>();
            response.put("authenticated", true);
            return ResponseEntity.ok(response);
        } else {
            // User is not authenticated
            return ResponseEntity.ok(Collections.singletonMap("authenticated", false));
        }
    }


}