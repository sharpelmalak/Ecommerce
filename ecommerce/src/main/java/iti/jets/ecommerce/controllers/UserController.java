package iti.jets.ecommerce.controllers;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.ResponseBody;

import iti.jets.ecommerce.dto.CustomerDTO;
import iti.jets.ecommerce.dto.OrderDTO;
import iti.jets.ecommerce.models.Order;
import iti.jets.ecommerce.services.CategoryService;
import iti.jets.ecommerce.services.CustomerService;
import iti.jets.ecommerce.services.OrderServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@Controller
@RequestMapping("/user")
public class UserController {
    
    
    @Autowired
    private CustomerService customerService;


    @Autowired
    private CategoryService categoryService;

    
    
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping("/account")
    public String showUserAccount(Model model, Principal principal) {
        model.addAttribute("CustomerDTO", customerService.getCustomerByUserName(principal.getName()));
        System.out.println("CustomerDTO : " + customerService.getCustomerByUserName(principal.getName()));
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
        
        // for debugging 
        for(OrderDTO orderDTO : orders){
            System.out.println("Order ID: " + orderDTO.getOrderId() + ", Status: " + orderDTO.getOrderStatus());
        }
    
        return "userOrderHistory";
    }

    @PostMapping("/account/update")
    public String updateCustomer(@ModelAttribute CustomerDTO customerDto, RedirectAttributes redirectAttributes) {
        int customerId = customerDto.getId();
        customerService.updateCustomer(customerId, customerDto);
        // Redirect to the GET method to reload the updated data
        // Add success message to redirect attributes
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        return "redirect:/user/account";
    }
}
