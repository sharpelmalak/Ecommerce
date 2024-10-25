package iti.jets.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDTO {
    private int cardId;
    private String paymentMethod;
    private String paymentStatus;
    private String transactionId;  // For Stripe/Fawry
    private String fawryReference;  // For Fawry
    private double amount;
    private String currency;

    // Getters and setters
}
