package iti.jets.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    private int customerId;
    private String customerName;
    private int cardId;
    private String type;
    private String number;
    private Integer expMonth;
    private Integer expYear;
    private String cvv;



}
