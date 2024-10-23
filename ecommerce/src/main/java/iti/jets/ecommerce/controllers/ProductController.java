package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.dto.ProductDTO;
import iti.jets.ecommerce.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /* Get all products */
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOs = productService.getAllProducts();
        return ResponseEntity.ok(productDTOs);
    }

    /* Get a specific product by ID */
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        ProductDTO returnedProductDTO = productService.getProductById(id);
        return ResponseEntity.ok(returnedProductDTO);
    }

    /* Get products by category */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category) {
        List<ProductDTO> productDTOs = productService.getProductsByCategory(category);
        return ResponseEntity.ok(productDTOs);
    }

    /* Get products by brand */
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ProductDTO>> getProductsByBrand(@PathVariable String brand) {
        List<ProductDTO> productDTOs = productService.getProductsByBrand(brand);
        return ResponseEntity.ok(productDTOs);
    }

    /* Get products by price range */
    @GetMapping("/price")
    public ResponseEntity<List<ProductDTO>> getProductsByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        List<ProductDTO> productDTOs = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(productDTOs);
    }

    /* Get products by category and price range */
    @GetMapping("/category/{category}/price")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryAndPrice(
            @PathVariable String category,
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        List<ProductDTO> productDTOs = productService.getProductsByCategoryAndPrice(category, minPrice, maxPrice);
        return ResponseEntity.ok(productDTOs);
    }

    /* Get products by name */
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> getProductsByName(@RequestParam String name) {
        List<ProductDTO> productDTOs = productService.getProductsByName(name);
        return ResponseEntity.ok(productDTOs);
    }
}
