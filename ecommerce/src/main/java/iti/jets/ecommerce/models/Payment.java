package iti.jets.ecommerce.models;

import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "card_id") // New field
    private Card card;

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;  // e.g., 'COD', 'Card', 'Fawry'

    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus;  // 'Pending', 'Completed', 'Failed'

    @Column(name = "transaction_id", length = 255)
    private String transactionId;  // For external transaction reference

    @Column(name = "amount", nullable = false, precision = 10)
    private double amount;

    @Column(name = "currency", length = 10)
    private String currency;  // e.g., 'USD', 'EGP'

    @Column(name = "customer_email", length = 255)
    private String customerEmail;  // For Stripe/Fawry

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt = Timestamp.from(Instant.now());
}
