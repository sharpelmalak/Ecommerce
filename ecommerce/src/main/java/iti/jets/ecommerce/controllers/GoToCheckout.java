package iti.jets.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("checkout")
public class GoToCheckout {
    @GetMapping
    public String checkout() {
        return "checkout";
    }

    @GetMapping("/confirm")
    public String confirm() {
        return "confirmation";
    }

    @GetMapping("/track")
    public String trackOrder() {
        return "tracking";
    }
}
