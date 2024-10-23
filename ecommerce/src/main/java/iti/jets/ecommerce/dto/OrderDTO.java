package iti.jets.ecommerce.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
public class OrderDTO {

    private int orderId;
    private int customerId;
    private Date orderDate;
    private String orderStatus;
    private double totalPrice;
    private Set<OrderItemDTO> orderItems;

    public OrderDTO(){}
    public OrderDTO(int orderId, int customerId, Date orderDate, String orderStatus, double totalPrice, Set<OrderItemDTO> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
    }
}
