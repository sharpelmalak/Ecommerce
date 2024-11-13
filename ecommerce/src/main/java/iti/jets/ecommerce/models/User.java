package iti.jets.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
    name = "user", 
    catalog = "ecommerce", 
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"), 
        @UniqueConstraint(columnNames = "username")
    }
)
@Inheritance(strategy = InheritanceType.JOINED)
@Data // Lombok annotation that generates getters, setters, equals, hashCode, toString
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "username", unique = true, nullable = false, length = 45)
    private String username;

    @Column(name = "password", nullable = false, length = 250)
    private String password;

    @Column(name = "email", unique = true, nullable = false, length = 45)
    private String email;
}

