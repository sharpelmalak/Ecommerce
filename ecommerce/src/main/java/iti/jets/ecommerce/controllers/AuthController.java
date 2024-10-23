package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.dto.CustomerDTO;
import iti.jets.ecommerce.services.CustomerService;
import iti.jets.ecommerce.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

    @PostMapping("/login")
    public String login(@RequestBody CustomerDTO customerDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        customerDTO.getUsername(), customerDTO.getPassword()));

        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(customerDTO.getUsername());
        }
        else {
            return "Login Failed";
        }
    }
}
