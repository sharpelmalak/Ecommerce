// package iti.jets.ecommerce.services;
//
//<<<<<<< HEAD
//import iti.jets.ecommerce.models.OrderItem;
//import iti.jets.ecommerce.models.OrderItemId;
//import iti.jets.ecommerce.repositories.OrderItemRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//=======
//// import iti.jets.ecommerce.models.OrderItem;
//// import iti.jets.ecommerce.repositories.OrderItemRepository;
//// import lombok.RequiredArgsConstructor;
//// import org.springframework.stereotype.Service;
//>>>>>>> 0e85513adfc9ddaa6032a9a8038e2a6569a8046b

// import java.util.List;

// @Service
// @RequiredArgsConstructor
// public class OrderItemService {

//     private OrderItemRepository orderItemRepository;

//     public void saveOrderItem(OrderItem orderItem) {
//         orderItemRepository.save(orderItem);
//     }
//
//<<<<<<< HEAD
//    public OrderItem getOrderItem(OrderItemId id) {
//        return orderItemRepository.getById(id);
//    }
//=======
////     public OrderItem getOrderItem(int id) {
////         return orderItemRepository.getById(id);
////     }
//>>>>>>> 0e85513adfc9ddaa6032a9a8038e2a6569a8046b

//     public List<OrderItem> getAllOrderItems() {
//         return orderItemRepository.findAll();
//     }

//     public void updateOrderItem(int id, OrderItem orderItem) {
//         OrderItem oldOrderItem = getOrderItem(id);
//         oldOrderItem.setQuantity(orderItem.getQuantity());
//         oldOrderItem.setCurrentPrice(orderItem.getCurrentPrice());
//         oldOrderItem.setQuantity(orderItem.getQuantity());
//         oldOrderItem.setProduct(orderItem.getProduct());
//         oldOrderItem.setOrder(orderItem.getOrder());
//         orderItemRepository.save(oldOrderItem);

//     }

//     public void deleteOrderItem(int id) {
//         orderItemRepository.deleteById(id);
//     }
//     public void deleteAllOrderItems() {
//         orderItemRepository.deleteAll();
//     }
// }
