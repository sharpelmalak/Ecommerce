package iti.jets.ecommerce.services;


import iti.jets.ecommerce.dto.CustomerDTO;
import iti.jets.ecommerce.mappers.CustomerMapper;
import iti.jets.ecommerce.models.Customer;
import iti.jets.ecommerce.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;


    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,PasswordEncoder passwordEncoder) {
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
}
