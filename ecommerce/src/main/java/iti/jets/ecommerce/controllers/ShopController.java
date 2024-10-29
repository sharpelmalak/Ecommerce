package iti.jets.ecommerce.controllers;


import iti.jets.ecommerce.dto.ProductDTO;
import iti.jets.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @GetMapping
    public String shop(@RequestParam(required = false)String param, Model model) {
        if (param != null) {
            model.addAttribute("query", param);
            model.addAttribute("search",true);
        }
        return "category";
    }

}
