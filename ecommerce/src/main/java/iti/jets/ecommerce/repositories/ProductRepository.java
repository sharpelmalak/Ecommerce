package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.Category;
import iti.jets.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Find products by category
    List<Product> findByCategory_Name(String categoryName);

    // find Active Products only
    List<Product> findAllByIsDeletedFalse(); // Fetch products that are not deleted

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

    // Function to select all unique brands
    @Query("SELECT DISTINCT p.brand FROM Product p WHERE p.brand IS NOT NULL")
    List<String> findAllUniqueBrands();

    // Function to select all unique materials
    @Query("SELECT DISTINCT p.material FROM Product p WHERE p.material IS NOT NULL")
    List<String> findAllUniqueMaterials();


    Page<Product> findByBrandInOrMaterialInOrPriceBetween(
            List<String> brands,
            List<String> materials,
            Float minPrice,
            Float maxPrice,
            Pageable pageable);

    Page<Product> findByCategoryId(
            Integer categoryId,
            Pageable pageable);

    Page<Product> findByCategoryIdAndBrandInOrMaterialInOrPriceBetween(
            Integer categoryId,
            List<String> brands,
            List<String> materials,
            Float minPrice,
            Float maxPrice,
            Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(
           String name,
            Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "(:brands IS NULL OR p.brand IN :brands) AND " +
            "(:materials IS NULL OR p.material IN :materials) AND " +
            "(p.price BETWEEN :minPrice AND :maxPrice)")
    Page<Product> findByFilters(
            @Param("categoryId") Integer categoryId,
            @Param("brands") List<String> brands,
            @Param("materials") List<String> materials,
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
            Pageable pageable
    );

}
