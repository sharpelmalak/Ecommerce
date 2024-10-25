package iti.jets.ecommerce.exceptions;

public class InvalidCardNumberException extends RuntimeException {
    public InvalidCardNumberException(String message) {
        super(message);
    }
}
