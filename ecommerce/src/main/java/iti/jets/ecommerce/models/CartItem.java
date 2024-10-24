package iti.jets.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Entity
@Table(name = "cart_item", catalog = "ecommerce")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements java.io.Serializable {

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "customerId", column = @Column(name = "customer_id", nullable = false)),
        @AttributeOverride(name = "productId", column = @Column(name = "product_id", nullable = false))
    })
    private CartItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return quantity == cartItem.quantity && product.equals(cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }
}
