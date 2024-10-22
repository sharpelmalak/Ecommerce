package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.dto.*;
import iti.jets.ecommerce.services.*;
import iti.jets.ecommerce.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

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
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOs = productService.getAllProducts();
        return ResponseEntity.ok(productDTOs);
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
    // Uncomment when needed

    /* Get all customers */
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    /* Get a specific customer by ID */
    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable int id) {
        CustomerDTO customerDTO = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerDTO);
    }

    /* Update a customer profile */
    @PutMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable int id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    /* Not Handled Yet : Haroun */
    // /* Delete a customer (soft delete) */
    // @DeleteMapping("/customer/{id}")
    // public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
    //     customerService.deleteCustomer(id);
    //     return ResponseEntity.ok("Customer deleted successfully");
    // }

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
}
