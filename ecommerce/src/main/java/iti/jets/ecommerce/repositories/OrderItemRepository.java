package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.Order;
import iti.jets.ecommerce.models.OrderItem;
import iti.jets.ecommerce.models.OrderItemId;

import org.springframework.data.jpa.repository.JpaRepository;

/* changed INTEGER in  JpaRepository<OrderItem, INTEGER>  be OrderItemId : haroun*/
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}
