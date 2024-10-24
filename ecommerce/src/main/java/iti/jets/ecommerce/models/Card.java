package iti.jets.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cardType;
    private String cardNumber;
    @Column(name = "exp_month", columnDefinition = "TINYINT")
    private int expMonth;
    @Column(name = "exp_year", columnDefinition = "SMALLINT")
    private int expYear;
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
