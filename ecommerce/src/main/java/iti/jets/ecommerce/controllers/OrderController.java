
package iti.jets.ecommerce.controllers;


import iti.jets.ecommerce.dto.CartItemDTO;
import iti.jets.ecommerce.dto.OrderDTO;
import iti.jets.ecommerce.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create new Order
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody int customerId, List<CartItemDTO> cartItems, String shippingAddress, String paymentMethod) {
        return ResponseEntity.ok(orderService.createOrder(customerId, cartItems, shippingAddress, paymentMethod));
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




}
