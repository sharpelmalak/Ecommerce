package iti.jets.ecommerce.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class RegisterDTO {
    private String name;
    private String username;
    private String password;
    private String email;
    private Date birthdate;
    private String job;
    private String address;
    private String phone;
}
