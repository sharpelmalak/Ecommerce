package iti.jets.ecommerce.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import iti.jets.ecommerce.exceptions.InvalidPromotionException;
import iti.jets.ecommerce.models.Customer;
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
    private CustomerRepository customerRepository;
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

    public List<Promotion> getActivePromotions() {
        LocalDate today = LocalDate.now();
        return promotionRepository.findActivePromotions(today);
        
    }

    public PromotionDTO getPromotionByName(String promotionName) {
        Promotion promotion = promotionRepository.findByName(promotionName).orElseThrow(() -> new InvalidPromotionException("Invalid Promotion"));
        return modelMapper.map(promotion, PromotionDTO.class);
    }

    public boolean usedBefore(String promotionName, int customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            return false; // Customer does not exist
        }
        return customer.getAppliedPromotions().contains(promotionName);
    }

    public boolean isValidPromotion(String promotionName, String country, int customerId) {
        LocalDate today = LocalDate.now();

        // Get the promotion by name and check existence
        PromotionDTO promotionDTO;
        try {
            promotionDTO = getPromotionByName(promotionName);
        } catch (InvalidPromotionException e) {
            return false; // Promotion not found
        }

        // Validate the promotion's country and date range
        if (!country.equalsIgnoreCase(promotionDTO.getCountry()) ||
                today.isBefore(promotionDTO.getStartDate()) || today.isAfter(promotionDTO.getEndDate())) {
            return false;
        }

        // Check if the customer has used this promotion before
        if (usedBefore(promotionName, customerId)) {
            return false;
        }

        // Associate this promotion with the customer if valid
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            Set<String> appliedPromotions = customer.getAppliedPromotions();
            appliedPromotions.add(promotionName);
            customer.setAppliedPromotions(appliedPromotions);
            customerRepository.save(customer);
        }

        // If all checks pass, the promotion is valid
        return true;
    }



    // other methods for updating, deleting, etc.
}
