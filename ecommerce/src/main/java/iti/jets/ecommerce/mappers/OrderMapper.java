//package iti.jets.ecommerce.mappers;
//
//import iti.jets.ecommerce.dto.OrderDTO;
//import iti.jets.ecommerce.models.Order;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingConstants;
//import org.mapstruct.factory.Mappers;
//
//@Mapper(componentModel = "spring")
//public interface OrderMapper {
//    //OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
//    // Note : same names mapped automatically
////    @Mapping(source = "id" ,target = "orderId")
////    @Mapping(source = "customer.id" ,target = "customerId")
////    @Mapping(source = "status" ,target = "orderStatus")
//    OrderDTO mapToDTO(Order order);
//
////    @Mapping(source = "orderId" ,target = "id")
////    @Mapping(source = "customerId" ,target = "customer.id")
////    @Mapping(source = "orderStatus" ,target = "status")
//    Order mapToEntity(OrderDTO dto);
//}
