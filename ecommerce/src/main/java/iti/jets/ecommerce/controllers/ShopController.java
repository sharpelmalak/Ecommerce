package iti.jets.ecommerce.controllers;


import iti.jets.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @GetMapping
    public String shop() {
        return "category";
    }

}
