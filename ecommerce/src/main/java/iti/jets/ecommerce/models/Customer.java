package iti.jets.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "customer", catalog = "ecommerce")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User implements java.io.Serializable {

    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate", nullable = false, length = 10)
    private Date birthdate;

    @Column(name = "job", length = 45, nullable = true)
    private String job;

    @Column(name = "phone", length = 45, nullable = true)
    private String phone;


    /* we should replace this with 'street' (but will affect any part it is used as address) : haroun */
    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "city", nullable = false, length = 100)
    private String city;
    
    @Column(name = "country", nullable = false, length = 100)
    private String country;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<CartItem> cartItems = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "customer_has_interests_category", catalog = "ecommerce",
            joinColumns = {@JoinColumn(name = "customer_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "category_id", nullable = false)})
    private Set<Category> categories = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "customer_wishlist", catalog = "ecommerce",
            joinColumns = {@JoinColumn(name = "customer_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "product_id", nullable = false)})
    private Set<Product> products = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<Order> orders = new HashSet<>(0);

    @ElementCollection
    @CollectionTable(name = "customer_promotions", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "promotion_code")
    private Set<String> appliedPromotions = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<Card> cards = new HashSet<>(0);

    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted = false;


    
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getId()+
                "birthdate=" + birthdate +
                ", job='" + job + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}
