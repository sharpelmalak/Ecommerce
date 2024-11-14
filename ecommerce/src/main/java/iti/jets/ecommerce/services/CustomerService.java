package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.*;
import iti.jets.ecommerce.exceptions.InvalidInputException;
import iti.jets.ecommerce.models.*;
import iti.jets.ecommerce.mappers.CustomerMapper;
import iti.jets.ecommerce.repositories.*;
import iti.jets.ecommerce.utils.ValidationUtils;
import lombok.extern.java.Log;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private CategoryRepository categoryRepository; // Make sure to create this repository


    @Autowired
    private PromotionService promotionService;


    @Autowired

    private UserRepository userRepository;


    // @Autowired does not have any effect if you are using the constructor : haroun
    public CustomerService(CustomerRepository customerRepository,ProductRepository productRepository,OrderRepository orderRepository,PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /* ==================================================== Registeration  ============================================= */
    @Transactional
    public boolean RegisterCustomer(CustomerDTO customerDTO) {
        if (customerRepository.existsByUsername(customerDTO.getUsername())) {
            return false; // Username already taken
        }

        // Check if email already exists
        if (customerRepository.existsByEmail(customerDTO.getEmail())) {
            return false;
        }

        // Fetch the categories (interests) based on the IDs
        Set<Category> selectedCategories = new HashSet<>();
        
        if (customerDTO.getCategoriesIds() != null) {
            for (Integer categoryId : customerDTO.getCategoriesIds()) {
                Category category = categoryRepository.findById(categoryId).orElse(null);
                if (category != null) {
                    selectedCategories.add(category);
                }
            }
        }

        /*Log the retrieved categories */
        System.out.println("Selected Categories: " + selectedCategories.size());

        // Use the mapper to convert DTO to entity
        Customer customer = CustomerMapper.toEntity(customerDTO, selectedCategories);

        // Hash the password before saving
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        customerRepository.save(customer); // Save the customer with interests
        return true; // Registration successful
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

    /* Get customer by ID */
    public CustomerDTO getCustomerByUserName(String username) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + username));
        return CustomerMapper.toDto(customer);
    }
    

    /* Get Customer Address */
    public CustomerAddressDTO getCustomerAddress(int customerId) {
        CustomerDTO customer = getCustomerById(customerId);
        return modelMapper.map(customer, CustomerAddressDTO.class);
    }

    // Edit Customer Address
    public CustomerAddressDTO editCustomerAddress(CustomerAddressDTO customerAddress){
        Customer authCustomer = customerRepository.findByEmail(customerAddress.getEmail()).orElse(null);
        CustomerDTO customer = getCustomerById(authCustomer.getId());
        System.out.println("addrss is : "+customerAddress.getAddress());

        // make sure the input is valid : not emtpy
        if(ValidationUtils.isValidCustomerAddress(customerAddress)){
            customer.setAddress(customerAddress.getAddress());
            customer.setCity(customerAddress.getCity());
            customer.setCountry(customerAddress.getCountry());
            customer.setPhone(customerAddress.getPhone());
            updateCustomer(authCustomer.getId(), customer);
        }
        else
            throw new InvalidInputException("Invalid Input Data");

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
        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setAddress(customerDTO.getAddress());
        existingCustomer.setCity(customerDTO.getCity());
        existingCustomer.setCountry(customerDTO.getCountry());
        existingCustomer.setPhone(customerDTO.getPhone());
        existingCustomer.setBirthdate(customerDTO.getBirthdate());
        existingCustomer.setJob(customerDTO.getJob());


        if (customerDTO.getUsername() != null && !customerDTO.getUsername().isEmpty()) {
            existingCustomer.setUsername(customerDTO.getUsername());
        }

        if (customerDTO.getPassword() != null && !customerDTO.getPassword().isEmpty()) {
            existingCustomer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        }

        // Update categories if they exist in DTO
        if (customerDTO.getCategoriesIds() != null) {
            List<Category> updatedCategories = categoryRepository.findAllById(customerDTO.getCategoriesIds());
            Set<Category> categories = new HashSet<>(updatedCategories); 
            existingCustomer.setCategories(categories);
        }


        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return CustomerMapper.toDto(updatedCustomer);
    }

    /* Delete a customer : soft delete */
    public String deleteCustomer(int id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer existingCustomer = customer.get();
            existingCustomer.setIsDeleted(true);
            customerRepository.save(existingCustomer);
            return "deleted";
        } else {
            throw new ResourceNotFoundException("Customer not found with ID: " + id);
        }
    }

    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username).isEmpty();
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
