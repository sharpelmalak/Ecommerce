package iti.jets.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "order", catalog = "ecommerce")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date", nullable = false, length = 19)
    private Timestamp orderDate = Timestamp.from(Instant.now());

    @Column(name = "shipping_cost")
    private Integer shippingCost = 50;

    @Column(name = "total_price", nullable = false, precision = 12)
    private double totalPrice;

    @Column(name = "shipping_address" , nullable = false)
    private String shippingAddress;

    @Column(name = "status", nullable = false)
    private String status;  // Possible values: "Placed", "Confirmed", "Delivered"

    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>(0);

    @Column(name = "payment_method")
    private String paymentMethod;

    public Order(Customer customer, float totalPrice, String status) {
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDate = Timestamp.from(Instant.now());
    }
}
