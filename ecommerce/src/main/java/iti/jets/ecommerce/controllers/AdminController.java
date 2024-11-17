package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.dto.*;
import iti.jets.ecommerce.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;
import java.util.UUID;




@Controller
@RequestMapping("/admin")
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
    @PostMapping("/product")
    public String createProduct(@ModelAttribute ProductDTO productDTO, @RequestParam int adminId, @RequestParam("imageFile") MultipartFile imageFile, Model model) {
        try {
            // Process and save the image
            if (!imageFile.isEmpty()) {
                // Define the path where the image will be saved
                String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/img/watch/";
                String imageFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path   imagePath = Paths.get(uploadDir + imageFileName);

                // Ensure the directory exists
                Files.createDirectories(imagePath.getParent());

                // Save the file locally
                Files.write(imagePath, imageFile.getBytes());

                // Set the image path in the productDTO
                productDTO.setImage( "/img/watch/"+imageFileName); 
            }
            // Save the product
            ProductDTO savedProduct = productService.createProduct(productDTO, adminId);
            model.addAttribute("productDTO", savedProduct);
            model.addAttribute("successMessage", "Product created successfully!");
            return "admin/product_success";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Image upload failed. Please try again.");
            return "admin/add-product"; 
        }
    }


    @GetMapping("/product/addForm")
    public String showAddProductForm(Model model,Principal principal) {
        int adminId = adminService.getAdminProfile(principal.getName()).getId();
        model.addAttribute("adminId", adminId);
        model.addAttribute("productDTO", new ProductDTO()); /*  Add an empty productDTO to the model */
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/add-product";
    }

    @GetMapping("/product/editForm")
    public String showEditProductForm(@RequestParam int id, Model model,Principal principal) {
        int adminId = adminService.getAdminProfile(principal.getName()).getId();
        model.addAttribute("adminId", adminId);
        ProductDTO productDTO = productService.getProductById(id);
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("productUpdated", false);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/edit-product";
    }
    
    @PostMapping("/product/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute ProductDTO productDTO, Model model) {
        // Call the service to update the product
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        model.addAttribute("successMessage", "Product updated successfully!");
        model.addAttribute("productDTO", updatedProduct);
        return "admin/product_update_success";
    }
    
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/products")
    public String getAllProducts(@RequestParam(defaultValue = "0" )int page,Model model) {
        int pageSize = 10;
        Page<ProductDTO> productDTOs = productService.getDefaultProducts(PageRequest.of(page, pageSize)); // Use the updated method
        List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();
        model.addAttribute("productList", productDTOs.getContent());
        model.addAttribute("TotalProductListCount",productService.getAllActiveProducts().size());
        model.addAttribute("categoryList", categoryDTOs);
        model.addAttribute("totalPages", productDTOs.getTotalPages());
        model.addAttribute("page", page); 
        return "admin/admin-panel";
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
        if (customerService.deleteCustomer(id).equals("deleted")) {
            return ResponseEntity.ok("Customer deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
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
    }

    /*  Get Order History for all customers */
    @GetMapping("/orders")
   
    public String getAllOrderHistory(@RequestParam(defaultValue = "0" )int page,Model model) {
        int size = 8;
        List<OrderDTO> totalOrders = orderServiceImpl.getAllOrders();
        Page<OrderDTO> orders = orderServiceImpl.getAllOrders(PageRequest.of(page,size));
        System.out.println("Num of Total Orders: "+totalOrders.size());
        System.out.println("Page Orders " + orders.getContent());
        model.addAttribute("orders", orders);
        model.addAttribute("totOrdersCount", totalOrders.size());
        model.addAttribute("totalPages", orders.getTotalPages());
        model.addAttribute("currentPage", page); 
        return "admin/orders";
    }
    

    @GetMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Integer orderId, @RequestParam String status) {
        System.out.println("orderStatusValue : " + status);
        orderServiceImpl.updateOrderStatus(orderId, status);
        return ResponseEntity.ok().build();
    }




    /* ============================================================================================ */
    /*                            Admin Profile Management                                          */
    /* ============================================================================================ */
    /* Get admin profile */
    @GetMapping("/profile")
    public String getAdminProfile(Model model, Principal principal) {        
        AdminDTO adminDTO = adminService.getAdminProfile(principal.getName());
        model.addAttribute("admin", adminDTO);
        return "admin/admin-profile"; // Assuming this is the name of your Thymeleaf template
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
    @GetMapping("/add-promotion-form")
    public String showPromotionForm(){
        return "admin/add-promotion";
    }

    @PostMapping("/promotion")
    public String createPromotion(@ModelAttribute PromotionDTO promotionDTO, Model model) {
    // Add success message and promotion details to the model
    model.addAttribute("successMessage", "Promotion created successfully!");
    model.addAttribute("promotionDTO", promotionService.createPromotion(promotionDTO));
    // Redirect to the success page
    return "admin/promotion-success"; 
}

}
