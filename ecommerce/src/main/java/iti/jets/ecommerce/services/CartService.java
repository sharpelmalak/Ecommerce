package iti.jets.ecommerce.services;


import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
import iti.jets.ecommerce.dto.CartItemDTO;
import iti.jets.ecommerce.dto.ProductDTO;
import iti.jets.ecommerce.exceptions.CartException;
import iti.jets.ecommerce.models.CartItem;
import iti.jets.ecommerce.repositories.CartItemRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CartService {

    private CartItemRepository cartItemRepository;

    private ProductService productService;

    @Autowired
    public CartService(CartItemRepository cartItemRepository, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }


    // Method to add a product to the cart
    public CartItemDTO addProductToCart(HttpSession session, int productId, int quantity) throws CartException {
        ProductDTO productDTO = productService.getProductById(productId);
        if (productDTO == null || productDTO.getQuantity() < quantity) {
            throw new CartException("Product not available or insufficient quantity.");
        }

        List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cart");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        CartItemDTO existingCartItem = cartItems.stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
        } else {
            CartItemDTO newCartItem = new CartItemDTO();
            newCartItem.setQuantity(quantity);
            newCartItem.setProduct(productDTO);
            cartItems.add(newCartItem);
        }

        session.setAttribute("cart", cartItems);

        return existingCartItem != null ? existingCartItem : cartItems.get(cartItems.size() - 1);
    }

    // Remove a product from the cart
    public void removeProductFromCart(HttpSession session, int productId) throws CartException {
        List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cart");

        if (cartItems == null) {
            throw new CartException("Cart is empty.");
        }

        boolean removed = cartItems.removeIf(item -> item.getProduct().getId() == productId);

        if (!removed) {
            throw new CartException("Product not found in cart.");
        }

        session.setAttribute("cart", cartItems);
    }

    // Update product quantity in the cart
    public CartItemDTO updateProductQuantityInCart(HttpSession session, int productId, int quantity) throws CartException {
        List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cart");

        if (cartItems == null) {
            throw new CartException("Cart is empty.");
        }

        CartItemDTO cartItem = cartItems.stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst()
                .orElseThrow(() -> new CartException("Product not found in cart."));

        cartItem.setQuantity(quantity);
        session.setAttribute("cart", cartItems);
        return cartItem;
    }
    // Clear all items from the cart
    public void clearCart(HttpSession session) {
        session.removeAttribute("cart");
    }

    public List<CartItemDTO> getCartItems(HttpSession session,Cookie[] cookies)
    {
        List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cart");
        if (cartItems == null) {
            cartItems = loadCartFromCookie(cookies,session);
        }
        return cartItems;
    }

    private String convertCartToJson(List<CartItemDTO> cartItems) {
        return new Gson().toJson(cartItems);
    }
    private List<CartItemDTO> convertJsonToCart(String cartJson) {
        return new Gson().fromJson(cartJson, new TypeToken<List<CartItemDTO>>(){}.getType());
    }


    public Cookie persistCartInCookie(HttpSession session) {
        List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cart");

        if (cartItems != null && !cartItems.isEmpty()) {
            // Convert cartItems to a JSON string
            String cartJson = convertCartToJson(cartItems);

            // Create a cookie to store the cart data
            Cookie cartCookie = new Cookie("cart", cartJson);
            cartCookie.setMaxAge(60 * 60 * 24 * 7); // Cookie valid for 7 days
            cartCookie.setPath("/"); // Set the path to ensure the cookie is available throughout the app

           return cartCookie;
        }
        return null;
    }


    public List<CartItemDTO> loadCartFromCookie(Cookie[] cookies,HttpSession session) {
        // Retrieve cart cookie
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart")) {
                    // Deserialize the cookie's value (JSON string) back into a list of CartItemDTO
                    String cartJson = cookie.getValue();
                    List<CartItemDTO> cart = convertJsonToCart(cartJson);
                    session.setAttribute("cart", cart);
                    return cart;
                }
            }
        }
        return new ArrayList<>();
    }
}
