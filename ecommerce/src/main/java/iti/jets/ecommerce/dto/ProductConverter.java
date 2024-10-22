package iti.jets.ecommerce.dto;

import iti.jets.ecommerce.models.Product;

public class ProductConverter {
    public static ProductDTO convertToDTO(Product product) {
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
