package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
