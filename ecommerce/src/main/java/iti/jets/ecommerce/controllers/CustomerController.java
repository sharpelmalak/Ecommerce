package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.dto.*;
import iti.jets.ecommerce.models.Customer;
import iti.jets.ecommerce.repositories.CustomerRepository;
import iti.jets.ecommerce.repositories.OrderRepository;
import iti.jets.ecommerce.repositories.ProductRepository;
import iti.jets.ecommerce.services.CustomerService;
import iti.jets.ecommerce.services.OrderServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private  CustomerService customerService;

    @Autowired
    private OrderServiceImpl orderServiceImpl;


    // Get Customer Profile
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable int id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    // Update Profile
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable int id, @RequestBody CustomerDTO customerDto) {
        customerService.updateCustomer(id, customerDto);
        return ResponseEntity.ok("Customer profile updated successfully.");
    }

    // Add Item to Cart
    @PostMapping("/{customerId}/cart/add")
    public ResponseEntity<String> addItemToCart(@PathVariable int customerId, @RequestBody CartItemDTO cartItemDto) {
        customerService.addToCart(customerId, cartItemDto);
        return ResponseEntity.ok("Item added to cart.");
    }

    // Remove Item from Cart
    @DeleteMapping("/{customerId}/cart/remove")
    public ResponseEntity<String> removeItemFromCart(@PathVariable int customerId, @RequestParam int productId) {
        customerService.removeFromCart(customerId, productId);
        return ResponseEntity.ok("Item removed from cart.");
    }

    // Place Order
    @PostMapping("/{customerId}/order")
    public ResponseEntity<String> placeOrder(@PathVariable OrderDTO orderDTO) {
        orderServiceImpl.createOrder(orderDTO);
        return ResponseEntity.ok("Order placed successfully.");
        /* in case we want to return OrderDTO instead of a successful message  */
        /*  OrderDTO placedOrderDTO  = orderServiceImpl.createOrder(orderDTO); 
            return ResponseEntity.ok(placedOrderDTO); */ 
    }

    // Get Order History
    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrderHistory(@PathVariable int customerId) {
        List<OrderDTO> orders = orderServiceImpl.getOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }

    // Pay for an Order
    @PostMapping("/{customerId}/order/{orderId}/pay")
    public ResponseEntity<String> payForOrder(@PathVariable int customerId, @PathVariable int orderId, @RequestBody PaymentDTO paymentDto) {
        customerService.payForOrder(customerId, orderId, paymentDto);
        return ResponseEntity.ok("Payment successful.");
    }
}
