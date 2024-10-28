package iti.jets.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressDTO {
    private String name;
    private String address;
    private String city;
    private String country;
    private String phone;
    private String email;

}
