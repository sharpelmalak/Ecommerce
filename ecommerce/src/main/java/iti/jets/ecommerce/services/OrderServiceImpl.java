package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.*;
import iti.jets.ecommerce.exceptions.ItemNotAvailableException;
import iti.jets.ecommerce.exceptions.ProductNotFoundException;
import iti.jets.ecommerce.exceptions.ResourceNotFoundException;
import iti.jets.ecommerce.exceptions.UnsupportedPaymentMethodException;
import iti.jets.ecommerce.models.*;
import iti.jets.ecommerce.repositories.CustomerRepository;
import iti.jets.ecommerce.repositories.OrderRepository;
import iti.jets.ecommerce.repositories.ProductRepository;
import iti.jets.ecommerce.services.payment.PaymentService;
import iti.jets.ecommerce.services.payment.PaymentServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageImpl;


import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartService cartService;
    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private ProductService productService;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private PaymentService paymentService;
    private EmailService emailService;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, ProductService productService,
                            CustomerRepository customerRepository, ProductRepository productRepository, PaymentService paymentService , EmailService emailService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.paymentService = paymentService;
        this.emailService = emailService;
        this.cartService = cartService;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(CheckoutRequest checkoutRequest, Customer customer) {
        // successful order is :
        // all cart items found
        // payment success or cash on delivery

        // Create a new order
        Order order = new Order();
        order.setCustomer(customer); // Associate the customer with the order
        order.setShippingAddress(customer.getAddress()+" "+ customer.getCity()+ " " + customer.getCountry());
        order.setPaymentMethod(checkoutRequest.getPaymentMethod());
        // First save the Order to generate an ID
        order.setOrderDate(new Timestamp(Instant.now().toEpochMilli()));
        order.setStatus("placed");
        orderRepository.save(order); // Save order to ensure ID is generated

        // Calculate total price and add order items
        double totalPrice = 0;
        Set<OrderItem> orderItems = new HashSet<>();

        // Now add order items with the generated order ID
        for (CartItemDTO item : checkoutRequest.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            // Handle Soft Delete
            if(product.isDeleted())
                throw new ProductNotFoundException("Product "+product.getName()+" is not available");

            if (product.getQuantity() >= item.getQuantity()) {
                double itemTotal = product.getPrice() * item.getQuantity();
                totalPrice += itemTotal;

                // Update product quantity and persist it
                product.setQuantity(product.getQuantity() - item.getQuantity());
                productRepository.save(product);

                // Create and configure order item
                OrderItem orderItem = new OrderItem();
                orderItem.setId(new OrderItemId(order.getId(), product.getId())); // Set the composite ID
                orderItem.setOrder(order); // Associate with the current order
                orderItem.setProduct(product);
                orderItem.setQuantity(item.getQuantity());
                orderItem.setCurrentPrice(product.getPrice());
                orderItems.add(orderItem);
            } else {
                throw new ProductNotFoundException("Product: "+product.getName()+" is out of stock");
            }
        }

        // Associate the items with the order and save again
        order.setShippingCost(checkoutRequest.getShippingCost());
        order.setOrderItems(orderItems);
        order.setTotalPrice(checkoutRequest.getTotalPrice());
        orderRepository.save(order); // Save again to persist the order with items

        // send payment request
        // result of payment [success,failed]
        // payment method : COD , Card , PayPal

        //First Check Payment Method
        if(checkoutRequest.getPaymentMethod() == null)
            throw new UnsupportedPaymentMethodException("Please Choose a Payment Method");

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
        paymentRequestDTO.setOrderId(order.getId());
        paymentRequestDTO.setPaymentMethod(checkoutRequest.getPaymentMethod());
        paymentRequestDTO.setCurrency("EGY");
        paymentRequestDTO.setAmount(totalPrice);
        paymentRequestDTO.setCustomerEmail(customer.getEmail());
        paymentRequestDTO.setCustomerMobile(customer.getPhone());
        PaymentDTO paymentDTO = paymentService.processPayment(paymentRequestDTO);

        // finally : after all steps ok -> save the order in db and notify customer
        // order created
        if (checkoutRequest.getPaymentMethod().equals("COD")  || paymentDTO.getPaymentStatus().equals("SUCCESS")) {
            order.setOrderDate(new Timestamp(Instant.now().toEpochMilli()));
            order.setStatus("Placed");
            order.setPaymentMethod(checkoutRequest.getPaymentMethod());

            // send email
            String customerEmail = customer.getEmail();
            OrderDTO orderDetails = modelMapper.map(order,OrderDTO.class); // Format your order details

            try{
                emailService.sendOrderConfirmation(customerEmail, orderDetails);
            }
            catch (MessagingException e){
                e.printStackTrace();
            }

            orderRepository.save(order);

        }
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public OrderDTO getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getOrdersByCustomer(int customerId) {
        return orderRepository.findByCustomerId(customerId)
                .stream().map((element) -> modelMapper.map(element, OrderDTO.class)).collect(Collectors.toList());
    }

    // For Admins
    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> returnedOrders = orders.stream().map((element) -> modelMapper.map(element, OrderDTO.class))
                .collect(Collectors.toList());
        return returnedOrders;
    }

    // For Admins
    @Override
    public Page<OrderDTO> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        List<OrderDTO> returnedOrdersDTOs = orders.getContent().stream().map((element) -> modelMapper.map(element, OrderDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(returnedOrdersDTOs,pageable,orders.getTotalElements());
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        // placed ,  shipped ,  out_for_delivery , delivered
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public OrderDTO trackOrder(int orderId, String email){
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Customer not found"));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        if(order.getCustomer().getId() == customer.getId()){
            return modelMapper.map(order, OrderDTO.class);
        }
        return null;

    }
}
