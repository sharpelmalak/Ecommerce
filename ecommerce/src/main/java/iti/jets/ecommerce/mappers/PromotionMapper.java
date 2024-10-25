package iti.jets.ecommerce.mappers;

import iti.jets.ecommerce.dto.PromotionDTO;
import iti.jets.ecommerce.models.Promotion;

public class PromotionMapper {
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
