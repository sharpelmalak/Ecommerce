package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    // You can define custom query methods here if needed
}