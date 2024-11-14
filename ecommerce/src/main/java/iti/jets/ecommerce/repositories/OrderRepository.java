package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.dto.OrderDTO;
import iti.jets.ecommerce.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomerId(int customerId);
    
}
