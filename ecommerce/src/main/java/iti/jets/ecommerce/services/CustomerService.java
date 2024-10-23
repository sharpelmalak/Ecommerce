package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.CustomerDTO;
import iti.jets.ecommerce.dto.CustomerDTOAdmin;
import iti.jets.ecommerce.mappers.CustomerMapper;
import iti.jets.ecommerce.models.Customer;
import iti.jets.ecommerce.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import iti.jets.ecommerce.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerDTO RegisterCustomer(CustomerDTO customerDTO) {

        customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        Customer customer = CustomerMapper.toEntity(customerDTO, new Customer());
        customerRepository.save(customer);
        return CustomerMapper.toDto(customer);
    }

    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }



    /* ============================ CRUD Operations for Customer ============================  */
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
