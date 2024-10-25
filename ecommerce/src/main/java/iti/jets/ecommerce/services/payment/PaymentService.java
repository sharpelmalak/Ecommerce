package iti.jets.ecommerce.services.payment;

import iti.jets.ecommerce.dto.PaymentDTO;
import iti.jets.ecommerce.dto.PaymentRequestDTO;

public interface PaymentService {
    PaymentDTO processPayment(PaymentRequestDTO paymentRequest);
}
