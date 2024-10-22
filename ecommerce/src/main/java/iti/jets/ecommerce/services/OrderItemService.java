package iti.jets.ecommerce.services;

import iti.jets.ecommerce.models.OrderItem;
import iti.jets.ecommerce.models.OrderItemId;
import iti.jets.ecommerce.repositories.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private OrderItemRepository orderItemRepository;

    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public OrderItem getOrderItem(OrderItemId id) {
        return orderItemRepository.getById(id);
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public void updateOrderItem(int id, OrderItem orderItem) {
        OrderItem oldOrderItem = getOrderItem(id);
        oldOrderItem.setQuantity(orderItem.getQuantity());
        oldOrderItem.setCurrentPrice(orderItem.getCurrentPrice());
        oldOrderItem.setQuantity(orderItem.getQuantity());
        oldOrderItem.setProduct(orderItem.getProduct());
        oldOrderItem.setOrder(orderItem.getOrder());
        orderItemRepository.save(oldOrderItem);

    }

    public void deleteOrderItem(int id) {
        orderItemRepository.deleteById(id);
    }
    public void deleteAllOrderItems() {
        orderItemRepository.deleteAll();
    }
}
