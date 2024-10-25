package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.PaymentRequestDTO;
import iti.jets.ecommerce.exceptions.InvalidEmailException;
import iti.jets.ecommerce.exceptions.InvalidCardNumberException;
import iti.jets.ecommerce.exceptions.CardExpirationDateException;
import iti.jets.ecommerce.exceptions.InvalidCvvException;
import iti.jets.ecommerce.models.Card;
import iti.jets.ecommerce.repositories.CardRepository;
import iti.jets.ecommerce.utils.ValidationUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    private CardRepository cardRepository;
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card save(Card card) {
        return cardRepository.save(card);
    }

    public Card findById(int id) {
        return cardRepository.findById(id).orElse(null);
    }

    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    public boolean checkCardAvailability(int cardId) {
        return findById(cardId) != null;
    }

    public void validateCardData(PaymentRequestDTO paymentRequest) {
        // Validate card number
        if (paymentRequest.getCardNumber() == null || !ValidationUtils.isValidCardNumber(paymentRequest.getCardNumber())) {
            throw new InvalidCardNumberException("Invalid card number.");
        }

        // Validate expiration date
        if (Integer.parseInt(paymentRequest.getExpMonth()) < 1 || Integer.parseInt(paymentRequest.getExpMonth()) > 12 || Integer.parseInt(paymentRequest.getExpYear()) < getCurrentYear()) {
            throw new CardExpirationDateException("Invalid expiration date.");
        } else if (Integer.parseInt(paymentRequest.getExpYear()) == getCurrentYear() && Integer.parseInt(paymentRequest.getExpMonth()) < getCurrentMonth()) {
            throw new CardExpirationDateException("Invalid expiration date.");
        }

        // Validate CVV
        if (paymentRequest.getCvv() == null || !ValidationUtils.isValidCvv(paymentRequest.getCvv())) {
            throw new InvalidCvvException("Invalid CVV.");
        }

        // Validate email format
        if (paymentRequest.getCustomerEmail() == null || !ValidationUtils.isValidEmail(paymentRequest.getCustomerEmail())) {
            throw new InvalidEmailException("Invalid customer email.");
        }
    }

    private int getCurrentYear() {
        return java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    }

    private int getCurrentMonth() {
        return java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1; // Months are 0-based
    }

}
