package iti.jets.ecommerce.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "product", catalog = "ecommerce")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "price", nullable = false, precision = 12)
    private float price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "brand")
    private String brand;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "image")
    private String image;       

    @Column(name = "is_deleted", nullable = true)
    private boolean isDeleted = false;

    // Watch-specific fields
    private String material;
    private int caseDiameter=0;
    private String waterResistance;
    private String gender;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<CartItem> cartItems = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<OrderItem> orderItems = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "customer_wishlist", catalog = "ecommerce", joinColumns = {
            @JoinColumn(name = "product_id", nullable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "customer_id", nullable = false) })
    private Set<Customer> customers = new HashSet<>(0);

    @Override
    public int hashCode() {
        return Objects.hash(price, quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;

        // Only check price and quantity for equality
        return Float.compare(this.price, other.price) == 0 && this.quantity == other.quantity;
    }

    public Product(Admin admin, Category category, String name, float price, int quantity) {
        this.admin = admin;
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(Admin admin, Category category, String name, float price, int quantity, String description,
            String image) {
        this.admin = admin;
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.image = image;
    }
}
