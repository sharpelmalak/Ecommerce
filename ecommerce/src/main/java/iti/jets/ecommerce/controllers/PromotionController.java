package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.dto.PromotionDTO;
import iti.jets.ecommerce.services.PromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/promotions")
public class PromotionController {
    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping("/validate")
    public ResponseEntity<PromotionDTO> isValidPromotion(@RequestParam String promotionName, @RequestParam String country) {
        if(promotionService.isValidPromotion(promotionName, country)) {
            PromotionDTO promotionDTO = promotionService.getPromotionByName(promotionName);
            return ResponseEntity.ok(promotionDTO);
        }
        return ResponseEntity.badRequest().build();
    }
}
