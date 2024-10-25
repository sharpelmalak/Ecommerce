package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.Category;
import iti.jets.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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


    // Function to select all unique brands
    @Query("SELECT DISTINCT p.brand FROM Product p WHERE p.brand IS NOT NULL")
    List<String> findAllUniqueBrands();

    // Function to select all unique materials
    @Query("SELECT DISTINCT p.material FROM Product p WHERE p.material IS NOT NULL")
    List<String> findAllUniqueMaterials();


    Page<Product> findByCategoryIdOrBrandInOrMaterialInOrPriceBetween(
            Integer categoryId,
            List<String> brands,
            List<String> materials,
            Float minPrice,
            Float maxPrice,
            Pageable pageable);

}
