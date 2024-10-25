package iti.jets.ecommerce.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iti.jets.ecommerce.dto.PromotionConverter;
import iti.jets.ecommerce.dto.PromotionDTO;
import iti.jets.ecommerce.models.Promotion;
import iti.jets.ecommerce.repositories.*;



@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;


    public PromotionDTO createPromotion(PromotionDTO promotionDTO) {
        Promotion promotion = new Promotion();
        promotion.setName(promotionDTO.getName());
        promotion.setDiscountPercentage(promotionDTO.getDiscountPercentage());
        promotion.setFreeShipping(promotionDTO.isFreeShipping());
        promotion.setCountry(promotionDTO.getCountry());
        promotion.setStartDate(promotionDTO.getStartDate());
        promotion.setEndDate(promotionDTO.getEndDate()); 
        return PromotionConverter.convertToDTO(promotionRepository.save(promotion)); 
    }


    
    public List<Promotion> getActivePromotionsForCountry(String country) {
        LocalDate today = LocalDate.now();
        return promotionRepository.findByCountryAndStartDateBeforeAndEndDateAfter(country, today, today);
    }


    // other methods for updating, deleting, etc.
}
