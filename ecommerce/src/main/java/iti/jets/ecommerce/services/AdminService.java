package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.*;
import iti.jets.ecommerce.models.*;
import iti.jets.ecommerce.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AdminService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AdminRepository adminRepository;  


    @Autowired
    private CategoryRepository categoryRepository;



    public Product createProduct(ProductDTO productDTO, int adminId) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());
    
        // Fetch the admin by ID
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        product.setAdmin(admin);
    
        // Fetch the category by ID from the productDTO and set it to the product
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);
    
        return productRepository.save(product);
    }
    


    // Update a product
    public Product updateProduct(int id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setImage(productDTO.getImage());
        // Update other properties if necessary
        return productRepository.save(existingProduct);
    }

    // Delete a product
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setDeleted(true); // Soft delete by setting the `isDeleted` flag
        productRepository.save(product);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
