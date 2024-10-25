package iti.jets.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import iti.jets.ecommerce.dto.ProductDTO;
import iti.jets.ecommerce.services.ProductService;


@Controller
public class HomeController {

    @Autowired
    private  ProductService productService; 

    @GetMapping("/home")
    public String home() {
        return "index";
    }
    @GetMapping("/customer/home")
    public String customerHome() {
        return "index";
    }

    @GetMapping("/admin/home")
    public String adminHome(Model model) {
        List<ProductDTO> productList = productService.getAllProducts();
        model.addAttribute("productList", productList);
        return "admin/admin-panel";
    }
}

