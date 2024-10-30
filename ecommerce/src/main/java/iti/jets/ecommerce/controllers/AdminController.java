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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;




@Controller
@SessionAttributes("productDTO")
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
    public String createProduct(@ModelAttribute ProductDTO productDTO, @RequestParam int adminId,Model model,
            RedirectAttributes redirectAttributes) {
        ProductDTO savedProduct = productService.createProduct(productDTO, adminId);
        // Add attributes to display on the success page
        redirectAttributes.addFlashAttribute("productAdded", true);
        redirectAttributes.addFlashAttribute("successMessage", "Product created successfully!");
        // Store productDTO in session
        model.addAttribute("productDTO", savedProduct);
        return "redirect:/admin/product_success"; // Redirect to the success page
    }

    @GetMapping("/product_success")
    public String productSuccessPage(Model model) {
        // Retrieve productDTO from the model
        ProductDTO productDTO = (ProductDTO) model.getAttribute("productDTO");
    
        // Add it to the model if needed for the view
        model.addAttribute("productDTO", productDTO);
        return "admin/product_success"; // Display the success message and details on this page
    }


    @GetMapping("/product/addForm")
    public String showAddProductForm(Model model,Principal principal) {
        int adminId = adminService.getAdminProfile(principal.getName()).getId();
        model.addAttribute("adminId", adminId);
        model.addAttribute("productDTO", new ProductDTO()); /*  Add an empty productDTO to the model */
        model.addAttribute("productAdded", false);
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
    public String updateProduct(@PathVariable int id, @ModelAttribute ProductDTO productDTO, Model model, RedirectAttributes redirectAttributes) {
        // Call the service to update the product
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        // Set success message and updated product
        redirectAttributes.addFlashAttribute("productUpdated", true);
        redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
        model.addAttribute("productDTO", updatedProduct);
        return "redirect:/admin/product_update_success";
    }
    
    @GetMapping("/product_update_success")
    public String productUpdateSuccessPage(Model model) {
        ProductDTO productDTO = (ProductDTO) model.getAttribute("productDTO");
        model.addAttribute("productDTO", productDTO);
        return "admin/product_update_success";
    }


    /* Delete a product (soft delete) */
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }


    /* method to get all the products (Active and non-Active) ,left for future usage : Haroun */
    /* Get all products */
    /*
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<ProductDTO> productDTOs = productService.getAllProducts();
        List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();
        model.addAttribute("productList", productDTOs);
        model.addAttribute("categoryList", categoryDTOs);
        return "admin/admin-panel";
        // return ResponseEntity.ok(productDTOs);
    }
    */

    @GetMapping("/products")
    public String getAllProducts(@RequestParam(defaultValue = "0" )int page,Model model) {
        int pageSize = 10;
        Page<ProductDTO> productDTOs = productService.getDefaultProducts(PageRequest.of(page, pageSize)); // Use the updated method
        List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();
        model.addAttribute("productList", productDTOs.getContent());
        model.addAttribute("categoryList", categoryDTOs);
        model.addAttribute("totalPages", productDTOs.getTotalPages());
        model.addAttribute("page", page); // Add the current page number to the model
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
        // return ResponseEntity.ok(orders);
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
    public String showPromotionForm(Model model) {
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
