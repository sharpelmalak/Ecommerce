package iti.jets.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentRequestDTO {
    private Integer orderId;           // Mandatory
    private Integer cardId;            // Optional (for existing card)
    private String cardNumber;      // Mandatory if no card is selected
    private String cardHolderName;  // Optional
    private String expMonth;  // Mandatory if no card is selected
    private String expYear;  // Mandatory if no card is selected
    private String cvv;             // Mandatory if no card is selected
    private String paymentMethod;   // Mandatory
    private double amount;          // Mandatory
    private String currency;        // Mandatory

    // Mandatory for Stripe
    private String stripeToken;

    // Mandatory for Fawry
    private String customerEmail;
    private String customerMobile;

}

