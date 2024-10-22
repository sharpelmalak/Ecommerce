package iti.jets.ecommerce.services;

import iti.jets.ecommerce.models.Order;
import iti.jets.ecommerce.repositories.OrderRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public Order getOrder(int id) {
        return orderRepository.getById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void updateOrder(int id, Order order) {
        Order oldOrder = getOrder(id);
        oldOrder.setOrderDate(order.getOrderDate());
        oldOrder.setOrderItems(order.getOrderItems());
        oldOrder.setCustomer(order.getCustomer());
        oldOrder.setTotalPrice(order.getTotalPrice());
        orderRepository.save(oldOrder);
    }

    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }
}
