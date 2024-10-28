package  iti.jets.ecommerce.mappers;

import iti.jets.ecommerce.dto.ProductDTO;
import iti.jets.ecommerce.models.Product;

public class ProductMapper {
    public static ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setDescription(product.getDescription());
        dto.setImage(product.getImage());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : 0); // Assuming Category has getId method
        dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : "default name"); // Assuming Category has getId method
        dto.setAdded_By(product.getAdmin() != null ? product.getAdmin().getName() : "default name"); // Assuming Category has getId method
        dto.setBrand(product.getBrand());
        dto.setDeleted(product.isDeleted());
        dto.setGender(product.getGender());
        dto.setMaterial(product.getMaterial());
        dto.setCaseDiameter(product.getCaseDiameter());
        dto.setWaterResistance(product.getWaterResistance());
        return dto;
    }

}
