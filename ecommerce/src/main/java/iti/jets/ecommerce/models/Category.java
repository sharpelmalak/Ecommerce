package iti.jets.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category", catalog = "ecommerce")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "customer_has_interests_category", catalog = "ecommerce",
            joinColumns = { @JoinColumn(name = "category_id", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "customer_id", nullable = false) })
    private Set<Customer> customers = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private Set<Product> products = new HashSet<>(0);
}
