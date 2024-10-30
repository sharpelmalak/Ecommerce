package iti.jets.ecommerce.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderTrackingRequest {
    private String orderId;
    private String email;
}
