package iti.jets.ecommerce.controllers;


import iti.jets.ecommerce.dto.CartItemDTO;
import iti.jets.ecommerce.exceptions.CartException;
import iti.jets.ecommerce.services.CartService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add a product to the cart
    @PostMapping("/add")
    public ResponseEntity<CartItemDTO> addToCart( @RequestParam int productId, @RequestParam int quantity,HttpServletRequest request,HttpServletResponse response) {
        try {
            CartItemDTO cartItem = cartService.addProductToCart(request.getSession(), productId, quantity);
            Cookie cookie = cartService.persistCartInCookie(request.getSession());
            if (cookie != null) {
                response.addCookie(cookie);
            }
            return ResponseEntity.ok(cartItem);
        } catch (CartException e) {
            return ResponseEntity.badRequest().body(null); // Return 400 with custom error
        }
    }

    // Get all items in the cart
    @GetMapping
    public ResponseEntity<List<CartItemDTO>> viewCart(HttpServletRequest request) {
        List<CartItemDTO> cartItems = cartService.getCartItems(request.getSession(),request.getCookies());
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/update")
    public ResponseEntity<CartItemDTO> updateCart(@RequestParam int productId, @RequestParam int quantity, HttpSession session) {
        try {
            CartItemDTO updatedItem = cartService.updateProductQuantityInCart(session, productId, quantity);
            return ResponseEntity.ok(updatedItem);
        } catch (CartException e) {
            return ResponseEntity.badRequest().body(null); // Return 400 with custom error
        }
    }

    // Remove a product from the cart
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromCart(@RequestParam int productId,HttpServletRequest request) {
        try {
            cartService.removeProductFromCart(request.getSession(), productId);
            return ResponseEntity.ok().build();
        } catch (CartException e) {
            return ResponseEntity.badRequest().build(); // Return 400 with custom error
        }
    }

    // Clear all items from the cart
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(HttpServletRequest request) {
        cartService.clearCart(request.getSession());
        return ResponseEntity.ok().build();
    }
}
