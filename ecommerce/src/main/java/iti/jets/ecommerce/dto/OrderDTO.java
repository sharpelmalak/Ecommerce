package iti.jets.ecommerce.dto;

import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
import java.util.Set;

import iti.jets.ecommerce.models.Order;
import jakarta.persistence.ElementCollection;

@Setter
@Getter
@ToString
public class OrderDTO {
    private int orderId;
    private int customerId;
    private Date orderDate;
    private String orderStatus;
    private int shippingCost;
    private String shippingAddress;
    private double totalPrice;
    private Set<OrderItemDTO> orderItems;

    public OrderDTO() {
    }

    public OrderDTO(int orderId, int customerId, Date orderDate, String orderStatus, int shippingCost, String shippingAddress, double totalPrice, Set<OrderItemDTO> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.shippingCost = shippingCost;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
    }
}
