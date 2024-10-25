package iti.jets.ecommerce.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDTO {
    private String name;
    private Double discountPercentage;
    private boolean freeShipping;
    private String country;
    private LocalDate startDate;
    private LocalDate endDate;
}
