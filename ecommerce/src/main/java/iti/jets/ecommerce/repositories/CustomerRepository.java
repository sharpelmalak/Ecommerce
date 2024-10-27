package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmailAndPassword(String email, String password);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByUsername(String username);
    // Method to check if a username already exists
    boolean existsByUsername(String username);

    // Optionally, add other methods as needed
    boolean existsByEmail(String email);
}
