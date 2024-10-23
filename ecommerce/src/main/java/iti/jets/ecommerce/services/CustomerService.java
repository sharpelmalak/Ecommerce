package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.CustomerDTO;
import iti.jets.ecommerce.mappers.CustomerMapper;
import iti.jets.ecommerce.models.Customer;
import iti.jets.ecommerce.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import iti.jets.ecommerce.exceptions.ResourceNotFoundException;

import java.util.List;
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

    /* Update customer profile */
    public CustomerDTO updateCustomer(int id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setPhone(customerDTO.getPhone());
        if (customerDTO.getPassword() != null && !customerDTO.getPassword().isEmpty()) {
            existingCustomer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        }
        // Update other fields as needed

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return CustomerMapper.toDto(updatedCustomer);
    }


    /* Not Handled Yet : Haroun */
    // /* Soft delete customer */
    // public void deleteCustomer(int id) {
    //     Customer customer = customerRepository.findById(id)
    //             .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

    //     if (customer.isDeleted()) {
    //         throw new RuntimeException("Customer is already deleted.");
    //     } else {
    //         customer.setDeleted(true); // Soft delete by setting the `isDeleted` flag
    //     }

    //     customerRepository.save(customer);
    // }
}
