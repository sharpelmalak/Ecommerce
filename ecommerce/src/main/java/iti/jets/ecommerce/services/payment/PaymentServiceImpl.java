package iti.jets.ecommerce.services.payment;

import iti.jets.ecommerce.dto.PaymentDTO;
import iti.jets.ecommerce.dto.PaymentRequestDTO;
import iti.jets.ecommerce.exceptions.UnsupportedPaymentMethodException;
import iti.jets.ecommerce.repositories.PaymentRepository;
import iti.jets.ecommerce.services.CardService;
import iti.jets.ecommerce.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private Map<String, PaymentStrategy> paymentStrategies;

    public PaymentServiceImpl(Map<String, PaymentStrategy> paymentStrategies) {
        this.paymentStrategies = paymentStrategies;
    }

    @Override
    public PaymentDTO processPayment(PaymentRequestDTO paymentRequest) {

        PaymentStrategy strategy = paymentStrategies.get(paymentRequest.getPaymentMethod());
        if (strategy == null) {
            throw new UnsupportedPaymentMethodException("Unsupported payment method: " + paymentRequest.getPaymentMethod());
        }

        return strategy.processPayment(paymentRequest);
    }
}
