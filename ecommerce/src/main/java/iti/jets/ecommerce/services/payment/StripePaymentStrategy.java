//package iti.jets.ecommerce.services.payment;
//
//import iti.jets.ecommerce.dto.PaymentDTO;
//import iti.jets.ecommerce.dto.PaymentRequestDTO;
//import org.springframework.stereotype.Service;
//
//@Service("Stripe")
//public class StripePaymentStrategy implements PaymentStrategy {
//
//    @Override
//    public PaymentDTO processPayment(PaymentRequestDTO paymentRequest) {
//        // Integrate with Stripe API
//        // Send payment request to Stripe, get response
//        // Example (pseudocode):
//        /*
//        Stripe.apiKey = "your_stripe_secret_key";
//        Map<String, Object> params = new HashMap<>();
//        params.put("amount", paymentRequest.getAmount() * 100);  // Stripe expects amount in cents
//        params.put("currency", "USD");
//        params.put("source", paymentRequest.getStripeToken());  // Tokenized card details
//        Charge charge = Charge.create(params);
//        */
//
//        PaymentDTO payment = new PaymentDTO();
//        payment.setPaymentMethod("Stripe");
//        payment.setTransactionId("stripe_transaction_id");
//        payment.setPaymentStatus("Completed");
//        payment.setAmount(paymentRequest.getAmount());
//        return payment;
//    }
//}
