package iti.jets.ecommerce.dto;

import lombok.Data;

@Data
public abstract class UserDTO {
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
}
