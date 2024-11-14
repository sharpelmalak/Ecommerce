package iti.jets.ecommerce.utils;

import iti.jets.ecommerce.dto.CustomerAddressDTO;

import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@.+\\..+$");
    private static final Pattern EGYPTIAN_PHONE_PATTERN =
            Pattern.compile("^(\\+20|0020|0)?(10|11|12|15)\\d{8}$");
    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Z][a-z]+(?:[ -][A-Z][a-z]+)*$");



    public static boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{16}") && luhnCheck(cardNumber);
    }

    public static boolean isValidCvv(String cvv) {
        return cvv.matches("\\d{3,4}");
    }

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidName(String name){
        return NAME_PATTERN.matcher(name).matches();
    }
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return EGYPTIAN_PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    private static boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = n - 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    //Customer Address Validation
    public static boolean isValidCustomerAddress(CustomerAddressDTO customerAddressDTO) {
        if(customerAddressDTO.getName().length() == 0 || customerAddressDTO.getName() == null) return false;
        if(customerAddressDTO.getAddress().length() == 0 || customerAddressDTO.getAddress() == null) return false;
        if(customerAddressDTO.getCity().length() == 0 || customerAddressDTO.getCity() == null) return false;
        if(customerAddressDTO.getCountry().length() == 0 || customerAddressDTO.getCountry() == null) return false;
        if(customerAddressDTO.getPhone().length() == 0 || customerAddressDTO.getPhone() == null) return false;
        if(customerAddressDTO.getEmail().length() == 0 || customerAddressDTO.getEmail() == null) return false;
        if(!isValidName(customerAddressDTO.getAddress())) return false;
        if(!isValidName(customerAddressDTO.getCity())) return false;
        if(!isValidName(customerAddressDTO.getCountry())) return false;
        if(!isValidPhoneNumber(customerAddressDTO.getPhone())) return false;
        if(!isValidEmail(customerAddressDTO.getEmail())) return false;
        return true;
    }

}
