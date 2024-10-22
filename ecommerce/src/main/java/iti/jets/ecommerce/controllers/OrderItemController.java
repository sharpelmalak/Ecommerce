<<<<<<< HEAD
//package iti.jets.ecommerce.controllers;
//
//import iti.jets.ecommerce.models.Order;
//import iti.jets.ecommerce.models.OrderItem;
//import iti.jets.ecommerce.models.OrderItemId;
//import iti.jets.ecommerce.services.OrderItemService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/orders-items")
//@RequiredArgsConstructor
//public class OrderItemController {
//
//    private final OrderItemService orderItemService;
//
//    @PostMapping
//    public void saveOrderItem(@RequestBody OrderItem orderItem) {
//        orderItemService.saveOrderItem(orderItem);
//    }
//
//    @GetMapping("/{id}")
//    public OrderItem getOrderItem(@PathVariable int id) {
//        return orderItemService.getOrderItem(id);
//    }
//
//    @GetMapping
//    public List<OrderItem> getAllOrderItems() {
//        return orderItemService.getAllOrderItems();
//    }
//
//    @PutMapping("/{id}")
//    public void updateOrderItem(@PathVariable int id , @RequestBody OrderItem orderItem) {
//        orderItemService.updateOrderItem(id,orderItem);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteOrderItem(@PathVariable int id) {
//        orderItemService.deleteOrderItem(id);
//    }
//
//    @DeleteMapping
//    public void deleteAllOrderItems() {
//        orderItemService.deleteAllOrderItems();
//    }
//
//
//
//}
=======
// package iti.jets.ecommerce.controllers;

// import iti.jets.ecommerce.models.Order;
// import iti.jets.ecommerce.models.OrderItem;
// import iti.jets.ecommerce.models.OrderItemId;
// import iti.jets.ecommerce.services.OrderItemService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/orders-items")
// @RequiredArgsConstructor
// public class OrderItemController {

//     private final OrderItemService orderItemService;

//     @PostMapping
//     public void saveOrderItem(@RequestBody OrderItem orderItem) {
//         orderItemService.saveOrderItem(orderItem);
//     }

//     @GetMapping("/{id}")
//     public OrderItem getOrderItem(@PathVariable int id) {
//         return orderItemService.getOrderItem(id);
//     }

//     @GetMapping
//     public List<OrderItem> getAllOrderItems() {
//         return orderItemService.getAllOrderItems();
//     }

//     @PutMapping("/{id}")
//     public void updateOrderItem(@PathVariable int id , @RequestBody OrderItem orderItem) {
//         orderItemService.updateOrderItem(id,orderItem);
//     }

//     @DeleteMapping("/{id}")
//     public void deleteOrderItem(@PathVariable int id) {
//         orderItemService.deleteOrderItem(id);
//     }

//     @DeleteMapping
//     public void deleteAllOrderItems() {
//         orderItemService.deleteAllOrderItems();
//     }



// }
>>>>>>> harounBranch
