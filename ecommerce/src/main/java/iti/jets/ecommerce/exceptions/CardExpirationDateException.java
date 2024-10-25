package iti.jets.ecommerce.exceptions;

public class CardExpirationDateException extends RuntimeException{
    public CardExpirationDateException(String message){
        super(message);
    }
}
