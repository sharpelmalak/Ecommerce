package iti.jets.ecommerce.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class CustomerRegistrationDTO {
    private String name;
    private String email;
    private String username;
    private String password;
    private Date birthdate;
}
