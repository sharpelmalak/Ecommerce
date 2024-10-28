package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {
    List<Card> findByCustomerId(int customerId);
}
