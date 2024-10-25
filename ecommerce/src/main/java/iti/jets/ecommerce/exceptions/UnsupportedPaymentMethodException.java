package iti.jets.ecommerce.exceptions;

public class UnsupportedPaymentMethodException extends UnsupportedOperationException {
    public UnsupportedPaymentMethodException(String message) {
        super(message);
    }
}
