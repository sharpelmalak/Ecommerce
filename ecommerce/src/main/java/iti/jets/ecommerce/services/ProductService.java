package iti.jets.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import iti.jets.ecommerce.mappers.ProductMapper;
import iti.jets.ecommerce.dto.ProductDTO;
import iti.jets.ecommerce.exceptions.ResourceNotFoundException;
import iti.jets.ecommerce.models.*;
import iti.jets.ecommerce.repositories.AdminRepository;
import iti.jets.ecommerce.repositories.CategoryRepository;
import iti.jets.ecommerce.repositories.ProductRepository;

@Service
public class ProductService {


    private ProductRepository productRepository;

    private AdminRepository adminRepository;  

    private CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, AdminRepository adminRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.adminRepository = adminRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductDTO createProduct(ProductDTO productDTO, int adminId) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());
        product.setDeleted(productDTO.isDeleted());
        product.setBrand(productDTO.getBrand());
        product.setWaterResistance(productDTO.getWaterResistance());
        product.setCaseDiameter(productDTO.getCaseDiameter());
        product.setMaterial(productDTO.getMaterial());
        product.setGender(productDTO.getGender());
        
        // Fetch the admin by ID or throw ResourceNotFoundException if not found
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with ID: " + adminId));
        product.setAdmin(admin);
    
        // Fetch the category by ID from the productDTO and set it to the product
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + productDTO.getCategoryId()));
        product.setCategory(category);
        
        Product savedProduct = productRepository.save(product);
        return ProductMapper.convertToDTO(savedProduct);
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
        existingProduct.setBrand(productDTO.getBrand());
        existingProduct.setGender(productDTO.getGender());
        existingProduct.setWaterResistance(productDTO.getWaterResistance());
        existingProduct.setCaseDiameter(productDTO.getCaseDiameter());
        Product updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.convertToDTO(updatedProduct);
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
                .map(ProductMapper::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<ProductDTO> getAllActiveProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::convertToDTO)
                .collect(Collectors.toList());
    }



    /* Get product by ID */
    public ProductDTO getProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return ProductMapper.convertToDTO(product);
    }

    public boolean checkProductAvailability(int productId, int requiredQuantity) {
        // Fetch the product from the repository
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the product's available stock is greater than or equal to the required quantity
        return product.getQuantity() >= requiredQuantity;
    }

    
    /* ============= Get product by category , price Range , and brand or Category and brand together */
    
    /*  Get products by category and return as DTOs */
    public List<ProductDTO> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategory_Name(category);
        return products.stream()
                .map(ProductMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    /*  Get products by brand and return as DTOs */
    public List<ProductDTO> getProductsByBrand(String brand) {
        List<Product> products = productRepository.findByBrand(brand);
        return products.stream()
                .map(ProductMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    /*  Get products by price range and return as DTOs */
    public List<ProductDTO> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return products.stream()
                .map(ProductMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    /*  Get products by category and price range, return as DTOs */
    public List<ProductDTO> getProductsByCategoryAndPrice(String category, Double minPrice, Double maxPrice) {
        List<Product> products = productRepository.findByCategory_NameAndPriceBetween(category, minPrice, maxPrice);
        return products.stream()
                .map(ProductMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    /*  Get products by name (partial match), return as DTOs */
    public List<ProductDTO> getProductsByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return products.stream()
                .filter(product -> product.isDeleted()==false)
                .map(ProductMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<String> getAllBrands()
    {
        return productRepository.findAllUniqueBrands();
    }

    public List<String> getAllMaterials()
    {
        return productRepository.findAllUniqueMaterials();
    }

    public Page<ProductDTO> getProducts(Integer categoryId, List<String> brands, List<String> materials, Float minPrice, Float maxPrice, Pageable pageable) {
        // If no filters are provided, fetch a default set of products
        if ((brands == null || brands.isEmpty()) && (materials == null || materials.isEmpty()) && minPrice == null && maxPrice == null) {
            if(categoryId == null)
            {
                return getDefaultProducts(pageable);
            }
            Page<Product> productPage = productRepository.findByCategoryIdAndIsDeletedFalse(categoryId,pageable); // You can change this to your preferred default query

            List<ProductDTO> productDTOs = productPage.getContent().stream()
                    .map(product -> ProductMapper.convertToDTO(product))
                    .collect(Collectors.toList());

            return new PageImpl<>(productDTOs, pageable, productPage.getTotalElements());
        } else {

            return getFilteredProducts(categoryId, brands, materials, minPrice, maxPrice, pageable);
        }
    }

    public Page<ProductDTO> getProducts (String name, Pageable pageable) {
        // If no filters are provided, fetch a default set of products
            Page<Product> productPage = productRepository.findByNameContainingIgnoreCaseAndIsDeletedFalse(name,pageable); // You can change this to your preferred default query
            List<ProductDTO> productDTOs = productPage.getContent().stream()
                   // .filter(product -> product.isDeleted()==false)
                    .map(product -> ProductMapper.convertToDTO(product))
                    .collect(Collectors.toList());
            return new PageImpl<>(productDTOs, pageable, productPage.getTotalElements());
    }

    public Page<ProductDTO> getDefaultProducts(Pageable pageable) {
        // Fetch the first page of products (adjust the size as needed)
        Page<Product> productPage = productRepository.findAll(pageable); // You can change this to your preferred default query

        List<ProductDTO> productDTOs = productPage.getContent().stream()
                .map(product -> ProductMapper.convertToDTO(product))
                .collect(Collectors.toList());

        return new PageImpl<>(productDTOs, pageable, productPage.getTotalElements());
    }

    public Page<ProductDTO> getFilteredProducts(Integer categoryId, List<String> brands, List<String> materials, Float minPrice, Float maxPrice, Pageable pageable) {
        // Set default minPrice and maxPrice if they are null
        if (minPrice == null) minPrice = 0.0F;
        if (maxPrice == null) maxPrice = Float.MAX_VALUE;

        // Build the specification with the filter criteria
        Specification<Product> spec = buildProductSpecification(categoryId, brands, materials, minPrice, maxPrice);

        // Fetch filtered products
        Page<Product> productPage = productRepository.findAll(spec, pageable);
        List<ProductDTO> productDTOs = productPage.getContent().stream()
                    .map(product -> ProductMapper.convertToDTO(product))
                    .collect(Collectors.toList());
        return new PageImpl<>(productDTOs, pageable, productPage.getTotalElements());
    }

    public List<ProductDTO> getFirst8Products() {
        List<Product> products = productRepository.findTop8ByIsDeletedFalseOrderByIdAsc();
        return products.stream()
                .map(ProductMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getLast8Products() {
        List<Product> products = productRepository.findLast8ProductsNotDeleted();
        return products.stream()
                .map(ProductMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    private Specification<Product> buildProductSpecification(Integer categoryId, List<String> brands, List<String> materials, Float minPrice, Float maxPrice) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.isFalse(root.get("isDeleted"));

            // Filter by category if present
            if (categoryId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("category").get("id"), categoryId));
            }

            // Filter by brands if present
            if (brands != null && !brands.isEmpty()) {
                predicate = cb.and(predicate, root.get("brand").in(brands));
            }

            // Filter by materials if present
            if (materials != null && !materials.isEmpty()) {
                predicate = cb.and(predicate, root.get("material").in(materials));
            }

            // Filter by price range
            predicate = cb.and(predicate, cb.between(root.get("price"), minPrice, maxPrice));

            return predicate;
        };
    }

}
