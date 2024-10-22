package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
