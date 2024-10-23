package iti.jets.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iti.jets.ecommerce.dto.ProductConverter;
import iti.jets.ecommerce.dto.ProductDTO;
import iti.jets.ecommerce.exceptions.ResourceNotFoundException;
import iti.jets.ecommerce.models.*;
import iti.jets.ecommerce.repositories.AdminRepository;
import iti.jets.ecommerce.repositories.CategoryRepository;
import iti.jets.ecommerce.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AdminRepository adminRepository;  

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductDTO createProduct(ProductDTO productDTO, int adminId) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());
        
        // Fetch the admin by ID or throw ResourceNotFoundException if not found
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with ID: " + adminId));
        product.setAdmin(admin);
    
        // Fetch the category by ID from the productDTO and set it to the product
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + productDTO.getCategoryId()));
        product.setCategory(category);
    
        Product savedProduct = productRepository.save(product);
        return ProductConverter.convertToDTO(savedProduct);   
    }

    /* Update a product */
    public ProductDTO updateProduct(int id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setImage(productDTO.getImage());
        // Update other properties if necessary

        Product updatedProduct = productRepository.save(existingProduct);
        return ProductConverter.convertToDTO(updatedProduct);
    }

    /* Delete a product (soft delete) */
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        
        if (product.isDeleted()) {
            throw new ResourceNotFoundException("Product with id : "+ id + " is already deleted");
        } else {
            product.setDeleted(true); // Soft delete by setting the `isDeleted` flag
        }
        productRepository.save(product);
    }

    /* Get all products */
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    /* Get product by ID */
    public ProductDTO getProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return ProductConverter.convertToDTO(product);
    }
}

