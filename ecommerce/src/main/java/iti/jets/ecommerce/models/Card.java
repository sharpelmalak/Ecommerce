package iti.jets.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int cardId;
    private String cardType;
    private String cardNumber;
    @Column(name = "exp_month", nullable = false)
    @Min(1) // Minimum value for month
    @Max(12) // Maximum value for month
    private Integer expMonth;

    @Column(name = "exp_year", nullable = false)
    @Min(2024) // Adjust this for the current/future years.
    private Integer expYear;

    @Column(name = "cvv", nullable = false)
    @Size(min = 3, max = 4) // Depending on your card type (3 or 4 digits)
    private String cvv;

    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
