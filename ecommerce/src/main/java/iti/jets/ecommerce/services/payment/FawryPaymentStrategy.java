//package iti.jets.ecommerce.services.payment;
//
//import iti.jets.ecommerce.dto.PaymentDTO;
//import iti.jets.ecommerce.dto.PaymentRequestDTO;
//import org.springframework.stereotype.Service;
//
//@Service("Fawry")
//public class FawryPaymentStrategy implements PaymentStrategy {
//
//    @Override
//    public PaymentDTO processPayment(PaymentRequestDTO paymentRequest) {
//        // Integrate with Fawry API (pseudocode):
//        /*
//        Fawry API call to create a payment request:
//        {
//          "merchantCode": "your_merchant_code",
//          "customerMobile": paymentRequest.getCustomerMobile(),
//          "customerEmail": paymentRequest.getCustomerEmail(),
//          "amount": paymentRequest.getAmount(),
//          "currency": "EGP"
//        }
//        Fawry returns a payment reference to complete the transaction.
//        */
//
//        PaymentDTO payment = new PaymentDTO();
//        payment.setPaymentMethod("Fawry");
//        payment.setFawryReference("fawry_payment_reference");
//        payment.setPaymentStatus("Pending");  // Fawry may allow payment at a terminal later
//        payment.setAmount(paymentRequest.getAmount());
//        return payment;
//    }
//}
//
