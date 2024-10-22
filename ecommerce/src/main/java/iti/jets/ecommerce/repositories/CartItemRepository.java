package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.CartItem;
import iti.jets.ecommerce.models.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
}
