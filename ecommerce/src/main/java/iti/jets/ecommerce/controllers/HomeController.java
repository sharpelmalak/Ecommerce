package iti.jets.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home/test")
    public String home() {
        return "index"; // This returns the index.html template from the templates directory
    }
}
