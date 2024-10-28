package iti.jets.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import iti.jets.ecommerce.dto.CustomerDTO;
import iti.jets.ecommerce.services.CategoryService;
import iti.jets.ecommerce.services.CustomerService;


@Controller
@RequestMapping("/user")
public class UserController {
    

    @Autowired
    private CustomerService customerService;


    @Autowired
    private CategoryService categoryService;

    @GetMapping("/account/{id}")
    public String showUserAccount(@PathVariable int id,Model model) {
        model.addAttribute("CustomerDTO", customerService.getCustomerById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "userAcc";
    }
}
