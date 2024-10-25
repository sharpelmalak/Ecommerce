package iti.jets.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class HomeController {

    @GetMapping("/home/test")
    public String home() {
        return "index"; // This returns the index.html template from the templates directory
    }

    // @GetMapping("/home")
    // public String login() {
    //     return "login"; // If "home/login.html" is under templates folder for Thymeleaf or JSP
    // }

    
    @GetMapping("/home")
    public String adminlogin() {
        return "adminlogin"; // If "home/login.html" is under templates folder for Thymeleaf or JSP
    }

    @GetMapping("/home/admin")
    public String adminpannel() {
        return "admin-panel"; // Return the name of the Thymeleaf template
    }

}
