package iti.jets.ecommerce.services;

import iti.jets.ecommerce.dto.OrderDTO;
import iti.jets.ecommerce.models.Order;
import iti.jets.ecommerce.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    private OrderRepository orderRepository;
    private ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        //Order newOrder = orderMapper.mapToEntity(orderDTO);
        Order newOrder = modelMapper.map(orderDTO, Order.class);
        newOrder = orderRepository.save(newOrder);
        return modelMapper.map(newOrder, OrderDTO.class);
    }

    @Override
    public OrderDTO getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        //List<OrderDTO> returnedOrders = orders.stream().map(orderMapper::mapToDTO).collect(Collectors.toList());
        List<OrderDTO> returnedOrders = orders.stream().map((element) -> modelMapper.map(element, OrderDTO.class)).collect(Collectors.toList());
        return returnedOrders;
    }


    @Override
    public void updateOrderStatus(int orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }
}
