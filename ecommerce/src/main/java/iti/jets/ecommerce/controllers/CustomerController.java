package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.dto.*;

import iti.jets.ecommerce.services.CustomerService;
import iti.jets.ecommerce.services.OrderServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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

    // Get Customer Address
    @GetMapping("/address/{id}")
    public ResponseEntity<CustomerAddressDTO> getCustomerAddress(@PathVariable int id) {
        CustomerAddressDTO customerAddress = customerService.getCustomerAddress(id);
        return ResponseEntity.ok(customerAddress);
    }

    // Edit Customer Address
    @PutMapping("/address/{id}")
    public ResponseEntity<CustomerAddressDTO> editCustomerAddress(@PathVariable int id, @RequestBody CustomerAddressDTO customerAddress) {
        return ResponseEntity.ok(customerService.editCustomerAddress(id,customerAddress));
    }

    // Update Profile
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable int id, @RequestBody CustomerDTO customerDto) {
        customerService.updateCustomer(id, customerDto);
        return ResponseEntity.ok("Customer profile updated successfully.");
    }

    /*  Place Order */
    @PostMapping("/{customerId}/order")
    public ResponseEntity<String> placeOrder(@RequestBody CheckoutRequest checkoutRequest) {
        orderServiceImpl.createOrder(checkoutRequest);
        return ResponseEntity.ok("Order placed successfully.");
        /* in case we want to return OrderDTO instead of a successful message  */
        /*  OrderDTO placedOrderDTO  = orderServiceImpl.createOrder(orderDTO); 
            return ResponseEntity.ok(placedOrderDTO); */ 
    }

    /*  Get Order History */
    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrderHistory(@PathVariable int customerId) {
        List<OrderDTO> orders = orderServiceImpl.getOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }

    /*  Pay for an Order */
    @PostMapping("/{customerId}/order/{orderId}/pay")
    public ResponseEntity<String> payForOrder(@PathVariable int customerId, @PathVariable int orderId, @RequestBody PaymentDTO paymentDto) {
        customerService.payForOrder(customerId, orderId, paymentDto);
        return ResponseEntity.ok("Payment successful.");
    }
}
