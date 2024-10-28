package iti.jets.ecommerce.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerDTO extends UserDTO{
    private Date birthdate;
    private String job;
    private String address;
    private String city;
    private String country;
    private String phone;
    private boolean isDeleted = false;
    private List<Integer> categoriesIds;
}
