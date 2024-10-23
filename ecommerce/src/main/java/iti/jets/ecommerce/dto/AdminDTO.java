package iti.jets.ecommerce.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;


@EqualsAndHashCode(callSuper = true)
@Data
public class AdminDTO extends UserDTO {
    private Date hireDate;
}
