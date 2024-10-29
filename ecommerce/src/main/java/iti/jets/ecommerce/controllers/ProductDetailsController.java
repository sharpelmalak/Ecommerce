package iti.jets.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import iti.jets.ecommerce.services.ProductService;


@Controller
@RequestMapping("/details")
public class ProductDetailsController {
    
    @Autowired
    private ProductService productService; 
    

    @GetMapping("/product")
    public String showProducDetails(@RequestParam int id, Model model){
        return "single-product";
    }


    @GetMapping("/{id}")
    public String showProducDetailsPage(@PathVariable int id,Model model){
        model.addAttribute("productDTO", productService.getProductById(id));
        model.addAttribute("category", productService.getProductById(id).getCategoryName());
        return "single-product";
    }

}
