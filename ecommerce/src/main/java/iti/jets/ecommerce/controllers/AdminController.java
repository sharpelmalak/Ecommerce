package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.dto.*;
import iti.jets.ecommerce.services.*;
import iti.jets.ecommerce.models.*;

import org.hibernate.event.spi.ResolveNaturalIdEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PromotionService promotionService;

    
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    
    /* ============================================================================================ */
    /*                            Admin Functionalities Related to Products                         */
    /* ============================================================================================ */
    /* Create a Product */
    @PostMapping("/product")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO, @RequestParam int adminId) {
        ProductDTO returnedProductDTO = productService.createProduct(productDTO, adminId);
        return ResponseEntity.ok(returnedProductDTO);
    }

    /* Update an existing product */
    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable int id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProductDTO = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProductDTO);
    }
    
    /* Delete a product (soft delete) */
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    /* Get all products */
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<ProductDTO> productDTOs = productService.getAllProducts();
        List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();
        model.addAttribute("productList", productDTOs);
        model.addAttribute("categoryList", categoryDTOs);
        return "admin/admin-panel";
        // return ResponseEntity.ok(productDTOs); /* in case we want to return a jason  */
    }

    /* Get a specific product by ID */
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int id) {
        ProductDTO returnedProductDTO = productService.getProductById(id);
        return ResponseEntity.ok(returnedProductDTO);
    }

    
    /* ============================================================================================ */
    /*                           Admin Functionalities Related to Customers                         */
    /* ============================================================================================ */
    /* Get all customers */
    @GetMapping("/customers")
    public String getAllCustomers(Model model) {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        model.addAttribute("customersList", customers);
        return "admin/customers";
        // return ResponseEntity.ok(customers);
    }

    /* Get a specific customer by ID */
    @GetMapping("/customer/{id}")
    public String getCustomerById(@PathVariable int id,Model model) {
        CustomerDTO customerDTO = customerService.getCustomerById(id);
        model.addAttribute("customer", customerDTO);
        return "admin/customer";
        // return ResponseEntity.ok(customerDTO); /* if we want to return a raw json */
    }


    /* Update a customer profile */
    @PutMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable int id, @RequestBody CustomerDTOAdmin customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomerByAdmin(id, customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    /* Not Handled Yet : Haroun */
    /* Delete a customer (soft delete) */
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
        if(customerService.deleteCustomer(id) == "deleted");{
            return ResponseEntity.ok("Customer deleted successfully");
        }
    }



    
    /*  Get Order History */
    @GetMapping("/{customerId}/orders")
    public String getOrderHistory(@PathVariable int customerId, Model model) {
        List<OrderDTO> orders = orderServiceImpl.getOrdersByCustomer(customerId);
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        model.addAttribute("customer", customerDTO);
        model.addAttribute("orders", orders);
        return "admin/orderHistory";
        // return ResponseEntity.ok(orders);
    }
    



    /* ============================================================================================ */
    /*                            Admin Profile Management                                          */
    /* ============================================================================================ */
    /* Get admin profile */
    @GetMapping("/profile/{adminId}")
    public ResponseEntity<AdminDTO> getAdminProfile(@PathVariable int adminId) {
        AdminDTO adminDTO = adminService.getAdminProfile(adminId);
        return ResponseEntity.ok(adminDTO);
    }

    
    /* Get all admins */
    @GetMapping("/profiles")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> adminDTOs = adminService.getAllAdmins();
        return ResponseEntity.ok(adminDTOs);
    }


    /* Update admin profile */
    @PutMapping("/profile/{adminId}")
    public ResponseEntity<AdminDTO> updateAdminProfile(@PathVariable int adminId, @RequestBody AdminDTO adminDTO) {
        AdminDTO updatedAdmin = adminService.updateAdminProfile(adminId, adminDTO);
        return ResponseEntity.ok(updatedAdmin);
    }

    /* Change admin password */
    @PutMapping("/profile/{adminId}/change-password")
    public ResponseEntity<String> changeAdminPassword(@PathVariable int adminId, @RequestBody PasswordChangeDTO passwordChangeDTO) {
        adminService.changePassword(adminId, passwordChangeDTO);
        return ResponseEntity.ok("Password changed successfully");
    }


    /* ================= Create Promotions  ==================== */
    @PostMapping("/promotion")
    public ResponseEntity<PromotionDTO> createPromotion(@RequestBody PromotionDTO promotionDTO) {
        return ResponseEntity.ok(promotionService.createPromotion(promotionDTO));
    }
}
