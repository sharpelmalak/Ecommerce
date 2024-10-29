package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.CartItemDTO;
import iti.jets.ecommerce.dto.CheckoutRequest;
import iti.jets.ecommerce.dto.OrderDTO;
import iti.jets.ecommerce.models.Customer;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(CheckoutRequest checkoutRequest , Customer customer);

    OrderDTO getOrderById(int orderId);
    List<OrderDTO> getOrdersByCustomer(int userId);
    List<OrderDTO> getAllOrders();

    void updateOrderStatus(int orderId, String status);
    OrderDTO trackOrder(int orderId, String customerEmail);

}
