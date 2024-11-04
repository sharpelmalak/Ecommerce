package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.Category;
import iti.jets.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Find products by category
    List<Product> findByCategory_Name(String categoryName);


    // Find products by brand
    List<Product> findByBrand(String brand);
    
    // Find products within a price range
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    
    // Find products by category and price range
    List<Product> findByCategory_NameAndPriceBetween(String categoryName, Double minPrice, Double maxPrice);

    // Find products by name containing a specific string (case insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p FROM Product p where p.isDeleted=false")
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p where p.isDeleted=false")
    List<Product> findAll();

    // Function to select all unique brands
    @Query("SELECT DISTINCT p.brand FROM Product p WHERE p.brand IS NOT NULL AND p.isDeleted = false")
    List<String> findAllUniqueBrands();

    // Function to select all unique materials
    @Query("SELECT DISTINCT p.material FROM Product p WHERE p.material IS NOT NULL AND p.isDeleted = false")
    List<String> findAllUniqueMaterials();


    Page<Product> findByIsDeletedFalseAndBrandInAndMaterialInAndPriceBetween(
            List<String> brands,
            List<String> materials,
            Float minPrice,
            Float maxPrice,
            Pageable pageable);

        Page<Product> findByIsDeletedFalseAndCategoryIdAndBrandInAndMaterialInAndPriceBetween(
                Integer categoryId,    
            List<String> brands,
                List<String> materials,
                Float minPrice,
                Float maxPrice,
                Pageable pageable);

    Page<Product> findByCategoryIdAndIsDeletedFalse(
            Integer categoryId,
            Pageable pageable);


    Page<Product> findByNameContainingIgnoreCaseAndIsDeletedFalse(
           String name,
            Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "((:brands IS NULL OR p.brand IN :brands) OR " +
            "(:materials IS NULL OR p.material IN :materials) OR " +
            "(p.price BETWEEN :minPrice AND :maxPrice)) AND" +
            "(p.isDeleted = false)")
    Page<Product> findByFilters(
            @Param("categoryId") Integer categoryId,
            @Param("brands") List<String> brands,
            @Param("materials") List<String> materials,
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
            Pageable pageable
    );


    // Method to find the first 8 products
    List<Product> findTop8ByIsDeletedFalseOrderByIdAsc();

    // Custom query to find the last 8 products
    @Query(value = "SELECT * FROM Product p WHERE p.is_deleted = false ORDER BY p.id DESC LIMIT 8", nativeQuery = true)
    List<Product> findLast8ProductsNotDeleted();

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
}
