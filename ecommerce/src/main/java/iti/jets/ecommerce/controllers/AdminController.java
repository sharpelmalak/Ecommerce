package iti.jets.ecommerce.controllers;


import iti.jets.ecommerce.dto.*;
import iti.jets.ecommerce.services.*;
import iti.jets.ecommerce.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private ProductService productService;









    @PostMapping("/product")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO, @RequestParam int adminId) {
        Product newProduct = productService.createProduct(productDTO, adminId);
        ProductDTO returnedProductDTO = this.convertToDTO(newProduct); 
        return ResponseEntity.ok(returnedProductDTO);
    }

    // Update an existing product
    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody ProductDTO productDTO) {
        Product updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete a product (soft delete)
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }


    // Get all products
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        /*  Convert List<Product> to List<ProductDTO> */
        List<ProductDTO> productDTOs = products.stream()
            .map(this::convertToDTO) // Use a method to convert
            .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setDescription(product.getDescription());
        dto.setImage(product.getImage());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : 0); // Assuming Category has getId method
        return dto;
    }
}
