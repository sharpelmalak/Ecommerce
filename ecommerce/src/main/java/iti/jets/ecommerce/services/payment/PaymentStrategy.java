package iti.jets.ecommerce.services.payment;

import iti.jets.ecommerce.dto.PaymentDTO;
import iti.jets.ecommerce.dto.PaymentRequestDTO;

public interface PaymentStrategy {
    PaymentDTO processPayment(PaymentRequestDTO paymentRequest);
}
