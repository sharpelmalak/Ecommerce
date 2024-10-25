package iti.jets.ecommerce.dto;

import lombok.Data;

@Data
public class CartItemDTO {
   private ProductDTO product;
   private int quantity;

}
