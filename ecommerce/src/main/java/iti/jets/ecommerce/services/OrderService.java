package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.CartItemDTO;
import iti.jets.ecommerce.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(int customerId, List<CartItemDTO> cartItems, String shippingAddress, String paymentMethod);

    OrderDTO getOrderById(int orderId);
    List<OrderDTO> getOrdersByCustomer(int userId);
    List<OrderDTO> getAllOrders();

    void updateOrderStatus(int orderId, String status);

}
