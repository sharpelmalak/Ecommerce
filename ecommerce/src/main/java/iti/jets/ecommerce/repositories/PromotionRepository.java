package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    // Find active promotions based on country and current date
    List<Promotion> findByCountryAndStartDateBeforeAndEndDateAfter(String country, LocalDate today, LocalDate today2);
    Optional<Promotion> findByName(String name);
}
