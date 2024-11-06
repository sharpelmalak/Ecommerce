package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.dto.PromotionDTO;
import iti.jets.ecommerce.models.Customer;
import iti.jets.ecommerce.repositories.CustomerRepository;
import iti.jets.ecommerce.services.PromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/promotions")
public class PromotionController {
    private final PromotionService promotionService;
    private final CustomerRepository customerRepository;

    public PromotionController(PromotionService promotionService, CustomerRepository customerRepository) {
        this.promotionService = promotionService;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/validate")
    public ResponseEntity<PromotionDTO> isValidPromotion(@RequestParam String promotionName, @RequestParam String country, Principal principal) {
        String username = principal.getName();
        Customer customer = customerRepository.findByUsername(username).orElse(null);
        int customerId = customer.getId();
        if(promotionService.isValidPromotion(promotionName, country,customerId)) {
            PromotionDTO promotionDTO = promotionService.getPromotionByName(promotionName);
            return ResponseEntity.ok(promotionDTO);
        }
        return ResponseEntity.badRequest().build();
    }
}
