package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.dto.CardDTO;
import iti.jets.ecommerce.dto.CustomerDTO;
import iti.jets.ecommerce.services.CardService;
import iti.jets.ecommerce.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private CardService cardService;
    private CustomerService customerService;

    public CardController(CardService cardService, CustomerService customerService) {
        this.customerService = customerService;
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity< List<CardDTO>> getCustomerCards(Principal principal){
        CustomerDTO customerDTO = customerService.getCustomerByUserName(principal.getName());
        return  ResponseEntity.ok(cardService.getAllCustomerCards(customerDTO.getId()));
    }

    @PostMapping
    public ResponseEntity<Void> createCard( @RequestBody CardDTO cardDTO , Principal principal){
        CustomerDTO customerDTO = customerService.getCustomerByUserName(principal.getName());
        cardDTO.setCustomerId(customerDTO.getId());
        cardService.save(cardDTO);
        return  ResponseEntity.ok().build();
    }
}

