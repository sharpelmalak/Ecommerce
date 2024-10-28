package iti.jets.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import iti.jets.ecommerce.dto.CustomerDTO;
import iti.jets.ecommerce.dto.OrderDTO;
import iti.jets.ecommerce.services.CategoryService;
import iti.jets.ecommerce.services.CustomerService;
import iti.jets.ecommerce.services.OrderServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/user")
public class UserController {
    

    @Autowired
    private CustomerService customerService;


    @Autowired
    private CategoryService categoryService;

    
    
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping("/account/{id}")
    public String showUserAccount(@PathVariable int id,Model model) {
        model.addAttribute("CustomerDTO", customerService.getCustomerById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "userAcc";
    }

     /*  Get Order History */
    @GetMapping("/{customerId}/orders")
    public String getOrderHistory(@PathVariable int customerId, Model model) {
        List<OrderDTO> orders = orderServiceImpl.getOrdersByCustomer(customerId);
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        model.addAttribute("customer", customerDTO);
        model.addAttribute("orders", orders);
        return "userOrderHistory";
    }

    @PostMapping("/account/update")
    public String updateCustomer(@ModelAttribute CustomerDTO customerDto) {
        int customerId = customerDto.getId();
        customerService.updateCustomer(customerId, customerDto);
        // Redirect to the GET method to reload the updated data
        return "redirect:/user/account/" + customerId;
    }
    
}
