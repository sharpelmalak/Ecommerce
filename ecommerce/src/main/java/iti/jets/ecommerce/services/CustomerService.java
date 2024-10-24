package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.*;
import iti.jets.ecommerce.models.*;
import iti.jets.ecommerce.mappers.CustomerMapper;
import iti.jets.ecommerce.repositories.CustomerRepository;
import iti.jets.ecommerce.repositories.OrderRepository;
import iti.jets.ecommerce.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import iti.jets.ecommerce.exceptions.ResourceNotFoundException;

import java.util.*;
import java.util.stream.Collectors;



@Service
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    
    private PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
  

    // @Autowired does not have any effect if you are using the constructor : haroun
    public CustomerService(CustomerRepository customerRepository,ProductRepository productRepository,OrderRepository orderRepository,PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerDTO RegisterCustomer(CustomerDTO customerDTO) {
        
        customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        Customer customer = CustomerMapper.toEntity(customerDTO);
        customerRepository.save(customer);
        return CustomerMapper.toDto(customer);
    }

    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }


    /* ==================================== Customer Actions ==============================  */
    // Add Item to Cart
    public void addToCart(int customerId, CartItemDTO cartItemDto) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        CartItem cartItem = new CartItem(customer, productRepository.findById(cartItemDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")), cartItemDto.getQuantity());
        customer.getCartItems().add(cartItem);
        customerRepository.save(customer);
    }

    // Remove Item from Cart
    public void removeFromCart(int customerId, int productId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customer.getCartItems().removeIf(item -> item.getProduct().getId() == productId);
        customerRepository.save(customer);
    }


    // Pay for an Order
    public void payForOrder(int customerId, int orderId, PaymentDTO paymentDto) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus("confirmed");
        orderRepository.save(order);
    }



















    /* ============================ CRUD Operations for Customer related to Admin Actions ============================  */
    /* Get all customers */
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerMapper::toDto)
                .collect(Collectors.toList());
    }

    /* Get customer by ID */
    public CustomerDTO getCustomerById(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
        return CustomerMapper.toDto(customer);
    }

    /* Update customer profile By Admin*/
    public CustomerDTO updateCustomerByAdmin(int id, CustomerDTOAdmin customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
        
        /* Incoming in CustomerDTOAdmin  */
        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setUsername(customerDTO.getUsername());
        existingCustomer.setEmail(customerDTO.getEmail());
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return CustomerMapper.toDto(updatedCustomer);
    }

    /* Update customer profile*/
    public CustomerDTO updateCustomer(int id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
        
        /* Incoming in DTO  */
        existingCustomer.setName(existingCustomer.getName());
        existingCustomer.setUsername(customerDTO.getUsername());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setAddress(existingCustomer.getAddress());
        existingCustomer.setPhone(existingCustomer.getPhone());
        existingCustomer.setBirthdate(existingCustomer.getBirthdate());
        existingCustomer.setJob(existingCustomer.getJob());

        if (customerDTO.getPassword() != null && !customerDTO.getPassword().isEmpty()) {
            existingCustomer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        }

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return CustomerMapper.toDto(updatedCustomer);
    }
    /* ============================ CRUD Operations for Customer related to Admin Actions ============================  */












    /* Delete a customer : soft delete */
    public String deleteCustomer(int id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer existingCustomer = customer.get();
            existingCustomer.setDeleted(true);
            customerRepository.save(existingCustomer);
            return "deleted";
        } else {
             throw new ResourceNotFoundException("Customer not found with ID: " + id);
        }
    }

}
