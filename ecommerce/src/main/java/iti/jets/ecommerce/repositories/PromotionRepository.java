package iti.jets.ecommerce.repositories;

import iti.jets.ecommerce.models.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    // Find active promotions based on country and current date
    List<Promotion> findByCountryAndStartDateBeforeAndEndDateAfter(String country, LocalDate today, LocalDate today2);
    Optional<Promotion> findByName(String name);
    @Query("SELECT p FROM Promotion p WHERE p.startDate <= :date AND p.endDate >= :date")
    List<Promotion> findActivePromotions(LocalDate date);


}
