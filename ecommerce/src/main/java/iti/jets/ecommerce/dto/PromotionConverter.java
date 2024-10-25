package iti.jets.ecommerce.dto;

import iti.jets.ecommerce.models.Promotion;

public class PromotionConverter {
    public static PromotionDTO convertToDTO(Promotion promotion) {
        PromotionDTO dto = new PromotionDTO();
        dto.setName(promotion.getName());
        dto.setCountry(promotion.getCountry());
        dto.setDiscountPercentage(promotion.getDiscountPercentage());
        dto.setStartDate(promotion.getStartDate());
        dto.setEndDate(promotion.getEndDate());
        dto.setFreeShipping(promotion.isFreeShipping());
        return dto;
    }
}
