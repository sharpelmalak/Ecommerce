package iti.jets.ecommerce.services;

import java.time.LocalDate;
import java.util.List;

import iti.jets.ecommerce.exceptions.InvalidPromotionException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iti.jets.ecommerce.mappers.PromotionMapper;
import iti.jets.ecommerce.dto.PromotionDTO;
import iti.jets.ecommerce.models.Promotion;
import iti.jets.ecommerce.repositories.*;



@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private ModelMapper modelMapper;


    public PromotionDTO createPromotion(PromotionDTO promotionDTO) {
        Promotion promotion = new Promotion();
        promotion.setName(promotionDTO.getName());
        promotion.setDiscountPercentage(promotionDTO.getDiscountPercentage());
        promotion.setFreeShipping(promotionDTO.isFreeShipping());
        promotion.setCountry(promotionDTO.getCountry());
        promotion.setStartDate(promotionDTO.getStartDate());
        promotion.setEndDate(promotionDTO.getEndDate()); 
        return PromotionMapper.convertToDTO(promotionRepository.save(promotion)); 
    }


    
    public List<Promotion> getActivePromotionsForCountry(String country) {
        LocalDate today = LocalDate.now();
        return promotionRepository.findByCountryAndStartDateBeforeAndEndDateAfter(country, today, today);
    }

    public PromotionDTO getPromotionByName(String promotionName) {
        Promotion promotion = promotionRepository.findByName(promotionName).orElseThrow(() -> new InvalidPromotionException("Invalid Promotion"));
        return modelMapper.map(promotion, PromotionDTO.class);
    }

    public boolean isValidPromotion(String promotionName, String country) {
        LocalDate today = LocalDate.now();

        // Get the promotion by name
        PromotionDTO promotionDTO = getPromotionByName(promotionName);

        // Check if promotion exists
        if (promotionDTO == null) {
            return false;
        }

        // Check if the promotion is valid for the given country
        if (!country.equalsIgnoreCase(promotionDTO.getCountry())) {
            return false;
        }

        // Check if today's date is within the promotion's valid date range
        if (today.isBefore(promotionDTO.getStartDate()) || today.isAfter(promotionDTO.getEndDate())) {
            return false;
        }

        // If all checks pass, the promotion is valid
        return true;
    }



    // other methods for updating, deleting, etc.
}
