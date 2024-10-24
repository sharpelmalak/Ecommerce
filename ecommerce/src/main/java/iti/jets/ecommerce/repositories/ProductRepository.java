package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.Category;
import iti.jets.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
