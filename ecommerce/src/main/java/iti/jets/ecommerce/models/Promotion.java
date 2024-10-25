package iti.jets.ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double discountPercentage;

    private boolean freeShipping;

    private String country;  /*  Geographic region (e.g., country or city) */

    private LocalDate startDate;

    private LocalDate endDate;

    // Lombok will handle the getters, setters, constructors, and other methods
}
