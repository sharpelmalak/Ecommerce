package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.*;
import iti.jets.ecommerce.exceptions.ItemNotAvailableException;
import iti.jets.ecommerce.exceptions.ProductNotFoundException;
import iti.jets.ecommerce.models.*;
import iti.jets.ecommerce.repositories.CustomerRepository;
import iti.jets.ecommerce.repositories.OrderRepository;
import iti.jets.ecommerce.repositories.ProductRepository;
import iti.jets.ecommerce.services.payment.PaymentService;
import iti.jets.ecommerce.services.payment.PaymentServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    private OrderRepository orderRepository;
    private ModelMapper     modelMapper;
    private ProductService productService;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private PaymentService paymentService;


    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, ProductService productService , CustomerRepository customerRepository, ProductRepository productRepository , PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.paymentService = paymentService;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(int customerId , List<CartItemDTO> cartItems, String shippingAddress, String paymentMethod) {
        // successful order is :
        // all input parameters not null
        // customer id is valid
        // all cart items found
        // payment success or cash on delivery

        // find the customer by customer id
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Create a new order
        Order order = new Order();
        order.setCustomer(customer);  // Associate the customer with the order
        order.setShippingAddress(customer.getAddress());
        order.setPaymentMethod(paymentMethod);

        // Calculate total price and add order items
        double totalPrice = 0;
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItemDTO item : cartItems) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));
            if (product != null) {
                double itemTotal = product.getPrice() * item.getQuantity();
                totalPrice += itemTotal;

                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(item.getQuantity());
                orderItem.setCurrentPrice(product.getPrice());
                orderItems.add(orderItem);
            }
        }
        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

        // send payment request
        // result of payment [success,failed]
        // payment method : COD , Card , PayPal
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
        paymentRequestDTO.setOrderId(order.getId());
        paymentRequestDTO.setPaymentMethod(paymentMethod);
        paymentRequestDTO.setCurrency("EGY");
        paymentRequestDTO.setAmount(totalPrice);
        paymentRequestDTO.setCustomerEmail(customer.getEmail());
        paymentRequestDTO.setCustomerMobile(customer.getPhone());
        PaymentDTO paymentDTO = paymentService.processPayment(paymentRequestDTO);

        // finally : after all steps ok -> save the order in db and notify customer order created
        if(paymentMethod.equals("COD") || paymentDTO.getPaymentStatus().equals("SUCCESS")) {
            order.setOrderDate( new Timestamp(Instant.now().toEpochMilli()));
            order.setStatus("placed");
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
        List<OrderDTO> returnedOrders = orders.stream().map((element) -> modelMapper.map(element, OrderDTO.class)).collect(Collectors.toList());
        return returnedOrders;
    }


    @Override
    public void updateOrderStatus(int orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }
}
