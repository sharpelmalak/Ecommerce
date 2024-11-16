package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.dto.CheckoutRequest;
import iti.jets.ecommerce.dto.OrderDTO;
import iti.jets.ecommerce.dto.OrderTrackingRequest;
import iti.jets.ecommerce.exceptions.ProductNotFoundException;
import iti.jets.ecommerce.exceptions.ResourceNotFoundException;
import iti.jets.ecommerce.models.Customer;
import iti.jets.ecommerce.repositories.CustomerRepository;
import iti.jets.ecommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private CustomerRepository customerRepository;

    @Autowired
    public OrderController(OrderService orderService, CustomerRepository customerRepository) {
        this.orderService = orderService;
        this.customerRepository = customerRepository;
    }

    // Create new Order
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CheckoutRequest checkoutRequest, Principal principal) {
        String username = principal.getName();
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found"));
        return ResponseEntity.ok(orderService.createOrder(checkoutRequest, customer));
    }

    // Get all orders for a specific user (Customer action)
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable int customerId) {
        List<OrderDTO> orders = orderService.getOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }

    // Get details of a specific order (Customer/Admin action)
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable int orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    // Get all orders (Admin action)
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Integer orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok().build();
    }

    
    // track the order
    @PostMapping ("/tracking")
    public ResponseEntity<OrderDTO> trackOrder(@RequestBody OrderTrackingRequest request) {
        System.out.println(Integer.parseInt(request.getOrderId()));
        OrderDTO trackedOrder = orderService.trackOrder(Integer.parseInt(request.getOrderId()), request.getEmail());
        return ResponseEntity.ok(trackedOrder);
    }

}
