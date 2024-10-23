package iti.jets.ecommerce.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerDTO extends UserDTO{
    private Date birthdate;
    private String job;
    private String address;
    private String phone;
}
