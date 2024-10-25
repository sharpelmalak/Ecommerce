package iti.jets.ecommerce.dto;

import iti.jets.ecommerce.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private int productId;
    private String productName; // Adding product name for display
    private int orderId;
    private int quantity;
    private double price;
}
