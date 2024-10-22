package iti.jets.ecommerce.mappers;

import iti.jets.ecommerce.dto.CustomerDTO;
import iti.jets.ecommerce.models.Customer;

public class CustomerMapper {

<<<<<<< HEAD
    // Convert UserDto to User Entity (general use for Customer or Admin)
    public static Customer toEntity(CustomerDTO customerDTO, Customer customer) {
        customer.setName(customerDTO.getName());
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setEmail(customerDTO.getEmail());
        customer.setBirthdate(customerDTO.getBirthdate());
        customer.setJob(customerDTO.getJob());     // Ensure this is being set
        customer.setPhone(customerDTO.getPhone()); // Ensure this is being set
        customer.setAddress(customerDTO.getAddress());
        System.out.println("inside mapper"+customer);
        // id is auto-generated, so no need to set it
        return customer;
    }

    // Convert User Entity to UserDto
    public static CustomerDTO toDto(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setUsername(customer.getUsername());
        dto.setPassword(customer.getPassword());
        dto.setEmail(customer.getEmail());
        dto.setBirthdate(customer.getBirthdate());
        dto.setJob(customer.getJob());
        dto.setAddress(customer.getAddress());
        dto.setPhone(customer.getPhone());
        return dto;
    }
=======
>>>>>>> harounBranch
}


