package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.dto.CardDTO;
import iti.jets.ecommerce.services.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity< List<CardDTO>> getCustomerCards(@PathVariable int customerId){
        return  ResponseEntity.ok(cardService.getAllCustomerCards(customerId));
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<Void> createCard(@PathVariable int customerId, @RequestBody CardDTO cardDTO){
        cardService.save(cardDTO);
        return  ResponseEntity.ok().build();
    }
}

