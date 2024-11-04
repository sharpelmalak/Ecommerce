package iti.jets.ecommerce.mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import iti.jets.ecommerce.dto.*;
import iti.jets.ecommerce.models.Category;
import iti.jets.ecommerce.models.Customer;
import jakarta.persistence.Convert;

public class CustomerMapper {

    // Convert UserDto to User Entity (general use for Customer or Admin)
    public static Customer toEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setEmail(customerDTO.getEmail());
        customer.setBirthdate(customerDTO.getBirthdate());
        customer.setJob(customerDTO.getJob());     // Ensure this is being set
        customer.setPhone(customerDTO.getPhone()); // Ensure this is being set
        customer.setAddress(customerDTO.getAddress());
        customer.setCity(customerDTO.getCity());
        customer.setCountry(customerDTO.getCountry());


        System.out.println("inside mapper"+customer);
        // id is auto-generated, so no need to set it
        return customer;
    }


    /* Convert CustomerDTO to Customer Entity */
    public static Customer toEntity(CustomerDTO dto, Set<Category> categories) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setUsername(dto.getUsername());
        customer.setPassword(dto.getPassword()); // You might want to handle password separately
        customer.setEmail(dto.getEmail());
        customer.setBirthdate(dto.getBirthdate());
        customer.setJob(dto.getJob());
        customer.setAddress(dto.getAddress());
        customer.setCity(dto.getCity());
        customer.setCountry(dto.getCountry());
        customer.setPhone(dto.getPhone());
        customer.setIsDeleted(dto.getIsDeleted());
        // Set categories to the customer
        customer.setCategories(categories); // Assuming Customer has a setCategories method
        
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
        dto.setCity(customer.getCity());
        dto.setCountry(customer.getCountry());
        dto.setPhone(customer.getPhone());
        dto.setIsDeleted(customer.getIsDeleted());
        dto.setCity(customer.getCity());
        dto.setCountry(customer.getCountry());

        System.out.println("Customer Categories: " + customer.getCategories());
        // If categories are associated with the customer, collect their IDs
        if (customer.getCategories() != null) {
        List<Integer> categoriesIds = customer.getCategories()
            .stream()
            .map(Category::getId) // Assuming Category has a getId() method
            .toList();
        Set<Integer>  categoriesIdsSet = new HashSet<>(categoriesIds);
        System.out.println("Size : " + categoriesIdsSet.size());
        dto.setCategoriesIds(categoriesIdsSet);
    }
        
        return dto;
    }

    // Convert User Entity to UserDto
    public static CustomerDTOAdmin toDtoCustomerAdmin(Customer customer) {
        CustomerDTOAdmin dto = new CustomerDTOAdmin();
        dto.setName(customer.getName());
        dto.setUsername(customer.getUsername());
        dto.setEmail(customer.getEmail());
        return dto;
    }

    

    
}


