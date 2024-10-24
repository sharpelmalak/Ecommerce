package iti.jets.ecommerce.dto;

import lombok.*;

@Data

public class OrderItemDTO {
    private int productId;
    private int orderId;
    private int quantity;
    private double price;



    // No-argument constructor
    public OrderItemDTO() {
    }

    public OrderItemDTO(int productId, int orderId, int quantity, double price) {
        this.productId = productId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.price = price;
    }
}
