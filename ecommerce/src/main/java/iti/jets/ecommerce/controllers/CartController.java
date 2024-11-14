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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add a product to the cart
    @GetMapping("/add")
    public ResponseEntity<CartItemDTO> addToCart(@RequestParam int productId, @RequestParam int quantity, HttpServletRequest request, HttpServletResponse response, Principal principal) {
        try {

            CartItemDTO cartItem = cartService.addProductToCart(request.getSession(), request.getCookies(),productId, quantity);
            Cookie cookie = cartService.persistCartInCookie(request.getSession());
            if(principal != null) {
                cartService.saveCart((List<CartItemDTO>)request.getSession().getAttribute("cart"),principal.getName());
            }
            if (cookie != null) {
                System.out.println("Cookie persisted");
                try {
                    response.addCookie(cookie);
                    System.out.println("Cookie persisted Done");
                } catch (Exception ex) {
                    System.out.println("Error while adding cookie: " + ex.getMessage());
                    ex.printStackTrace(); // Log the stack trace for detailed error information
                }
            }
            else {
                System.out.println("Cookie not persisted");
            }
            return ResponseEntity.ok(cartItem);
        } catch (CartException e) {
            return ResponseEntity.badRequest().body(null); // Return 400 with custom error
        }
    }

    // Get all items in the cart
    @GetMapping
    public String  viewCart(HttpServletRequest request, Model model,HttpServletResponse response) {

        List<CartItemDTO> cartItems = cartService.getCartItems(request.getSession(),request.getCookies(),response);
        double sum = 0.0;
        if(cartItems!=null && !cartItems.isEmpty())
        {
            sum = cartItems.stream()
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum();
        }
        model.addAttribute("cart", cartItems);
        model.addAttribute("sum", sum);
        return "cart";
    }

    @GetMapping("/update")
    public ResponseEntity<CartItemDTO> updateCart(@RequestParam int productId, @RequestParam int quantity, HttpSession session,HttpServletResponse response,Principal principal) {
        try {

            CartItemDTO updatedItem = cartService.updateProductQuantityInCart(session, productId, quantity);
            Cookie cookie = cartService.persistCartInCookie(session);
            if(principal != null) {
                cartService.saveCart((List<CartItemDTO>)session.getAttribute("cart"),principal.getName());
            }
            if (cookie != null) {
               response.addCookie(cookie);
            }
            return ResponseEntity.ok(updatedItem);
        } catch (CartException e) {
            return ResponseEntity.badRequest().body(null); // Return 400 with custom error
        }
    }

    // Remove a product from the cart
    @GetMapping("/remove/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable int productId,HttpServletRequest request,HttpServletResponse response,Principal principal) {
        try {
            cartService.removeProductFromCart(request.getSession(), productId);

            Cookie cookie = cartService.persistCartInCookie(request.getSession());
            
            if(principal != null) {
                cartService.saveCart((List<CartItemDTO>)request.getSession().getAttribute("cart"),principal.getName());
            }
            if (cookie != null) {
                response.addCookie(cookie);
            }
            return ResponseEntity.ok().build();
        } catch (CartException e) {
            return ResponseEntity.badRequest().build(); // Return 400 with custom error
        }
    }

    // Clear all items from the cart
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(HttpServletRequest request,HttpServletResponse response,Principal principal) {
        if(principal != null) {
            cartService.resetCart(principal.getName());
        }
        Cookie cookie = cartService.clearCart(request.getSession());
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    // Clear all items from the cart
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkCart(HttpServletRequest request) {
        boolean isChanged = cartService.checkCart(request.getSession());
        if (!isChanged) {
            return ResponseEntity.ok(Boolean.TRUE);
        }
        return ResponseEntity.ok(Boolean.FALSE);
    }
    

    // Clear all items from the cart
    @GetMapping("/checkEmpty")
    public ResponseEntity<Boolean> checkCartEmpty(HttpServletRequest request) {
        boolean isChanged = cartService.checkCartEmpty(request.getSession());
        if (isChanged) {
            return ResponseEntity.ok(Boolean.TRUE);
        }
        return ResponseEntity.ok(Boolean.FALSE);
    }


    // Get all items in the cart
    @GetMapping("/checkout")
    public ResponseEntity<List<CartItemDTO>>  cartCheckout(HttpServletRequest request,HttpServletResponse response, Model model) {
        List<CartItemDTO> cartItems = cartService.getCartItems(request.getSession(),request.getCookies(),response);
        if(cartItems!=null && !cartItems.isEmpty())
        {
            return ResponseEntity.ok(cartItems);
        }
        return ResponseEntity.badRequest().body(null);
    }
}
