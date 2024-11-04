package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmailAndPassword(String email, String password);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByUsername(String username);
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.categories WHERE c.username = :username")
    Optional<Customer> findByUsernameWithCategories(@Param("username") String username);

    // Method to check if a username already exists
    boolean existsByUsername(String username);

    // Optionally, add other methods as needed
    boolean existsByEmail(String email);
}
