package iti.jets.ecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest {
    private String shippingAddress;
    private List<CartItemDTO> items;
    private String paymentMethod;
    private double totalPrice;
    private int shippingCost;
}
