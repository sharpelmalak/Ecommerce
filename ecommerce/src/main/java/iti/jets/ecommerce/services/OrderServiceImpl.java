package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.OrderDTO;
import iti.jets.ecommerce.mappers.OrderMapper;
import iti.jets.ecommerce.models.Customer;
import iti.jets.ecommerce.models.Order;
import iti.jets.ecommerce.repositories.CustomerRepository;
import iti.jets.ecommerce.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderMapper orderMapper;
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderMapper orderMapper, OrderRepository orderRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order newOrder = orderMapper.mapToEntity(orderDTO);
        newOrder = orderRepository.save(newOrder);
        return orderMapper.mapToDTO(newOrder);
    }

    @Override
    public OrderDTO getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.mapToDTO(order);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> returnedOrders = orders.stream().map(orderMapper::mapToDTO).collect(Collectors.toList());
        return returnedOrders;
    }


    @Override
    public void updateOrderStatus(int orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }
}
