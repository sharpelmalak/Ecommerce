package iti.jets.ecommerce.controllers;

import iti.jets.ecommerce.models.Order;
import iti.jets.ecommerce.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void saveOrder(@RequestBody Order order) {
        orderService.saveOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable int id) {
        return orderService.getOrder(id);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/{id}")
    public void updateOrder(@PathVariable int id , @RequestBody Order order) {
        orderService.updateOrder(id,order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }

    @DeleteMapping
    public void deleteAllOrders() {
        orderService.deleteAllOrders();
    }



}
