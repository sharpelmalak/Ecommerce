package iti.jets.ecommerce.models;
// Generated Sep 2, 2024, 5:22:05 PM by Hibernate Tools 6.5.1.Final


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * OrderItem generated by hbm2java
 */
@Entity
@Table(name="order_item"
    ,catalog="shomya"
)
public class OrderItem  implements java.io.Serializable {


     private OrderItemId id;
     private Order order;
     private Product product;
     private int quantity;
     private float currentPrice;

    public OrderItem() {
    }

    public OrderItem(Order order, Product product, int quantity, float currentPrice) {
       this.id = new OrderItemId(order.getId(), product.getId());
       this.order = order;
       this.product = product;
       this.quantity = quantity;
       this.currentPrice = currentPrice;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="orderId", column=@Column(name="order_id", nullable=false) ), 
        @AttributeOverride(name="productId", column=@Column(name="product_id", nullable=false) ) } )
    public OrderItemId getId() {
        return this.id;
    }
    
    public void setId(OrderItemId id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id", nullable=false, insertable=false, updatable=false)
    public Order getOrder() {
        return this.order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="product_id", nullable=false, insertable=false, updatable=false)
    public Product getProduct() {
        return this.product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }

    
    @Column(name="quantity", nullable=false)
    public int getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
    @Column(name="current_price", nullable=false, precision=12)
    public float getCurrentPrice() {
        return this.currentPrice;
    }
    
    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }




}


