package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
