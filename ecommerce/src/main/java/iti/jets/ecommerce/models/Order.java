package iti.jets.ecommerce.models;
// Generated Sep 2, 2024, 5:22:05 PM by Hibernate Tools 6.5.1.Final


import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Order generated by hbm2java
 */
@Entity
@Table(name="order"
    ,catalog="ecommerce"
)
public class Order  implements java.io.Serializable {

     private int id;
     private Customer customer;
     private Timestamp orderDate;
     private float totalPrice;
     private PaymentMethod paymentMethod;

     private Set<OrderItem> orderItems = new HashSet<OrderItem>(0);

    public Order() {
    }



    public Order(Customer customer, float totalPrice) {
       this.customer = customer;
       this.orderDate = Timestamp.from(Instant.now());
       this.totalPrice = totalPrice;
    }
   
     @Id@GeneratedValue(strategy = GenerationType.IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="customer_id", nullable=false)
    public Customer getCustomer() {
        return this.customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="order_date", nullable=false, length=19)
    public Timestamp getOrderDate() {
        return this.orderDate;
    }
    
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    
    @Column(name="total_price", nullable=false, precision=12)
    public float getTotalPrice() {
        return this.totalPrice;
    }
    
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="order")
    public Set<OrderItem> getOrderItems() {
        return this.orderItems;
    }
    
    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="payment_method_id", nullable=false)
    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}


