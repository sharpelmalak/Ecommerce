package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO order);

    OrderDTO getOrderById(int orderId);
    List<OrderDTO> getAllOrders();

    void updateOrderStatus(int orderId, String status);

}
