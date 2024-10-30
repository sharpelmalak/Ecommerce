package iti.jets.ecommerce.services.payment;

import iti.jets.ecommerce.dto.PaymentDTO;
import iti.jets.ecommerce.dto.PaymentRequestDTO;
import org.springframework.stereotype.Service;


@Service("Paypal")
public class PaypalPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentDTO processPayment(PaymentRequestDTO paymentRequest) {
        // Mark the order as pending payment (manual payment later)
        PaymentDTO payment = new PaymentDTO();
        payment.setPaymentMethod("Paypal");
        payment.setPaymentStatus("SUCCESS");
        payment.setAmount(paymentRequest.getAmount());
        return payment;
    }
}
