package iti.jets.ecommerce.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "order_item", catalog = "ecommerce")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements java.io.Serializable {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "orderId", column = @Column(name = "order_id", nullable = false)),
            @AttributeOverride(name = "productId", column = @Column(name = "product_id", nullable = false))
    })
    private OrderItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "current_price", nullable = false, precision = 12)
    private float currentPrice;

    /*  Constructor without Lombok's @AllArgsConstructor for specific case */
    public OrderItem(Order order, Product product, int quantity, float currentPrice) {
        this.id = new OrderItemId(order.getId(), product.getId());
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
    }
}
