package iti.jets.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDTO {
    private String paymentMethod;
    private double amount;
    private String currency;
    private String paymentStatus;
}
