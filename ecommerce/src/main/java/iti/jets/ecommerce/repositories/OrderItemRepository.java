package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.Order;
import iti.jets.ecommerce.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
