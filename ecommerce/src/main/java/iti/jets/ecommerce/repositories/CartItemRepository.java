package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.CartItem;
import iti.jets.ecommerce.models.CartItemId;
import iti.jets.ecommerce.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

    @Override
    <S extends CartItem> List<S> saveAll(Iterable<S> entities);

    List<CartItem> findByCustomer(Customer customer);

    void deleteAllByCustomer(Customer customer);

}
