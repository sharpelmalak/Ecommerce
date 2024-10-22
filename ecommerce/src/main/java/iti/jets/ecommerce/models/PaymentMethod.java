package iti.jets.ecommerce.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@Table(name = "payment_method")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private boolean isDeleted = false;
}
