package iti.jets.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordChangeDTO {
    private String currentPassword;
    private String newPassword;
}
