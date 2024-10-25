package iti.jets.ecommerce.services.payment;

import iti.jets.ecommerce.dto.PaymentDTO;
import iti.jets.ecommerce.dto.PaymentRequestDTO;
import iti.jets.ecommerce.exceptions.ResourceNotFoundException;
import iti.jets.ecommerce.models.Card;
import iti.jets.ecommerce.models.Customer;
import iti.jets.ecommerce.models.Payment;
import iti.jets.ecommerce.repositories.CustomerRepository;
import iti.jets.ecommerce.repositories.OrderRepository;
import iti.jets.ecommerce.repositories.PaymentRepository;
import iti.jets.ecommerce.services.CardService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service("Card")
public class CardPaymentStrategy implements PaymentStrategy {

    private CardService cardService;
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private PaymentRepository PaymentRepository;
    private ModelMapper modelMapper;


    public CardPaymentStrategy(CardService cardService, OrderRepository orderRepository, CustomerRepository customerRepository, PaymentRepository paymentRepository, ModelMapper modelMapper) {
        this.cardService = cardService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.PaymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentDTO processPayment(PaymentRequestDTO paymentRequest) {
        Card card;

        // Validate card data
        cardService.validateCardData(paymentRequest);

        // Check if an existing card ID is provided
        if (paymentRequest.getCardId() != null) {
            // Retrieve the existing card
            card = cardService.findById(paymentRequest.getCardId());

        } else if (paymentRequest.getCardNumber() != null) {
            // Create a new Card if card details are provided
            card = new Card();
            card.setCardNumber(paymentRequest.getCardNumber());  // may want to tokenize or mask this
            card.setExpMonth(Integer.parseInt(paymentRequest.getExpMonth()));
            card.setExpYear(Integer.parseInt(paymentRequest.getExpYear()));
            card.setCvv(paymentRequest.getCvv());

            // Associate the card with the customer
            Customer customer = customerRepository.findByEmail(paymentRequest.getCustomerEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            card.setCustomer(customer);

            // Save the new card to the database
            card = cardService.save(card);
        } else {
            throw new IllegalArgumentException("Card information is required for payment.");
        }

        // Create and save the payment
        Payment payment = new Payment();
        payment.setOrder(orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found")));
        payment.setCard(card);  // Associate the card with the payment
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setAmount(paymentRequest.getAmount());
        payment.setCurrency(paymentRequest.getCurrency());
        payment.setPaymentStatus("Pending");  // Initial status
        // Save the payment in the database
        payment = PaymentRepository.save(payment);

        // Convert the saved payment entity to a DTO
        return modelMapper.map(payment, PaymentDTO.class);
    }


}
