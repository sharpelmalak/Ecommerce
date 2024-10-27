package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.*;
import iti.jets.ecommerce.models.*;
import iti.jets.ecommerce.mappers.CustomerMapper;
import iti.jets.ecommerce.repositories.CustomerRepository;
import iti.jets.ecommerce.repositories.OrderRepository;
import iti.jets.ecommerce.repositories.ProductRepository;

import org.modelmapper.ModelMapper;
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
    private final OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PromotionService promotionService;
  

    // @Autowired does not have any effect if you are using the constructor : haroun
    public CustomerService(CustomerRepository customerRepository,ProductRepository productRepository,OrderRepository orderRepository,PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /* ==================================================== Registeration  ============================================= */
    public CustomerDTO RegisterCustomer(CustomerDTO customerDTO) {
        
        customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        Customer customer = CustomerMapper.toEntity(customerDTO);
        customerRepository.save(customer);
        return CustomerMapper.toDto(customer);
    }

    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
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

    /* Get Customer Address */
    public CustomerAddressDTO getCustomerAddress(int customerId) {
        CustomerDTO customer = getCustomerById(customerId);
        return modelMapper.map(customer, CustomerAddressDTO.class);
    }

    // Edit Customer Address
    public CustomerAddressDTO editCustomerAddress(int customerId, CustomerAddressDTO customerAddress){
        CustomerDTO customer = getCustomerById(customerId);
        System.out.println("addrss is : "+customerAddress.getAddress());
        customer.setAddress(customerAddress.getAddress());
        customer.setCity(customerAddress.getCity());
        customer.setCountry(customerAddress.getCountry());
        customer.setPhone(customerAddress.getPhone());
        updateCustomer(customerId, customer);
        return customerAddress;
    }

    /* Update customer profile By Admin */
    public CustomerDTO updateCustomerByAdmin(int id, CustomerDTOAdmin customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        /* Incoming in CustomerDTOAdmin */
        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setUsername(customerDTO.getUsername());
        existingCustomer.setEmail(customerDTO.getEmail());
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return CustomerMapper.toDto(updatedCustomer);
    }

    /* Update customer profile */
    public CustomerDTO updateCustomer(int id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        /* Incoming in DTO */
        existingCustomer.setName(existingCustomer.getName());
        existingCustomer.setUsername(customerDTO.getUsername());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setAddress(customerDTO.getAddress());
        existingCustomer.setPhone(customerDTO.getPhone());
        existingCustomer.setBirthdate(customerDTO.getBirthdate());
        existingCustomer.setJob(existingCustomer.getJob());
        existingCustomer.setAddress(customerDTO.getAddress());
        existingCustomer.setCity(customerDTO.getCity());
        existingCustomer.setCountry(customerDTO.getCountry());


        if (customerDTO.getPassword() != null && !customerDTO.getPassword().isEmpty()) {
            existingCustomer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        }

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return CustomerMapper.toDto(updatedCustomer);
    }

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



    



    /* =========================== Promotions for customer Based on a specific Region (Country) =========================*/
    /* Get Promotion For a cutomer Based on its location (Country) */
    public List<Promotion> getPromotionsForCustomer(Customer customer) {
        String userCountry = customer.getCountry(); // Assume you store region in the User entity
        return promotionService.getActivePromotionsForCountry(userCountry);
    }

    
    /* Should be called at checkout point  */
    public Double calculatePriceWithPromotions(Customer Customer, Double originalPrice) {
        List<Promotion> promotions = getPromotionsForCustomer(Customer);
        Double finalPrice = originalPrice;
    
        for (Promotion promotion : promotions) {
            if (promotion.getDiscountPercentage() != null) {
                finalPrice -= originalPrice * (promotion.getDiscountPercentage() / 100);
            }
            if (promotion.isFreeShipping()) {
                // Apply free shipping logic
            }
        }
        return finalPrice;
    }

    // Pay for an Order
    public void payForOrder(int customerId, int orderId, PaymentDTO paymentDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus("confirmed");
        orderRepository.save(order);
    }
}
