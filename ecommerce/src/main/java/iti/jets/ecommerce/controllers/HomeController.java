package iti.jets.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "index";
    }
    @GetMapping("/customer/home")
    public String customerHome() {
        return "index";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "index";
    }
}

