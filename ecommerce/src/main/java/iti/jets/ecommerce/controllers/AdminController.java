package iti.jets.ecommerce.controllers;


import iti.jets.ecommerce.dto.ProductDTO;
import iti.jets.ecommerce.services.AdminService;
import iti.jets.ecommerce.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO, @RequestParam int adminId) {
        Product newProduct = adminService.createProduct(productDTO, adminId);
        return ResponseEntity.ok(newProduct);
    }

    // Update an existing product
    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody ProductDTO productDTO) {
        Product updatedProduct = adminService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete a product (soft delete)
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        adminService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    // Get all products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = adminService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
