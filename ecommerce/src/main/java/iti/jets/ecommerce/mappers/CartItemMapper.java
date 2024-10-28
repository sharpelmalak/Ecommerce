package iti.jets.ecommerce.mappers;


import iti.jets.ecommerce.dto.CartItemDTO;
import iti.jets.ecommerce.dto.CustomerDTO;
import iti.jets.ecommerce.models.CartItem;
import iti.jets.ecommerce.models.CartItemId;
import iti.jets.ecommerce.models.Customer;
import iti.jets.ecommerce.repositories.ProductRepository;
import iti.jets.ecommerce.services.ProductService;

import java.util.ArrayList;
import java.util.List;

public class CartItemMapper {


    // Convert User Entity to UserDto
    public static List<CartItemDTO> toDto(List<CartItem> cartItem) {
       List<CartItemDTO> cartItemDTOs = new ArrayList<>();
       for (CartItem item : cartItem) {
           CartItemDTO dto = new CartItemDTO();
           dto.setProduct(ProductMapper.convertToDTO(item.getProduct()));
           dto.setQuantity(item.getQuantity());
           cartItemDTOs.add(dto);
       }
       return cartItemDTOs;
    }

    public static List<CartItem> toEntity(List<CartItemDTO> cartItemDTO,Customer customer, ProductRepository productRepository) {
        List<CartItem> cartItems = new ArrayList<CartItem>();
        for(CartItemDTO dto : cartItemDTO) {
            CartItem cartItem = new CartItem();
            CartItemId cartItemId = new CartItemId();
            cartItemId.setCustomerId(customer.getId());
            cartItemId.setProductId(dto.getProduct().getId());
            cartItem.setId(cartItemId);
            cartItem.setQuantity(dto.getQuantity());
            cartItem.setProduct(productRepository.findById(dto.getProduct().getId()).get());
            cartItem.setCustomer(customer);
            cartItems.add(cartItem);
        }
        return cartItems;
    }
}
