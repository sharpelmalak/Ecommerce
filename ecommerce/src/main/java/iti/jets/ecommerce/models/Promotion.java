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

    @Column(name = "discount_percentage")
    private Double discountPercentage;

    @Column(name = "free_shipping")
    private boolean freeShipping = false;

    private String country;  /*  Geographic region (e.g., country or city) */

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    // Lombok will handle the getters, setters, constructors, and other methods
}
