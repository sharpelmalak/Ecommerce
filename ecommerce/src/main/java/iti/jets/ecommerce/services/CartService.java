package iti.jets.ecommerce.services;


import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
import iti.jets.ecommerce.dto.CartItemDTO;
import iti.jets.ecommerce.dto.ProductDTO;
import iti.jets.ecommerce.exceptions.CartException;
import iti.jets.ecommerce.mappers.CartItemMapper;
import iti.jets.ecommerce.models.CartItem;
import iti.jets.ecommerce.models.Customer;
import iti.jets.ecommerce.repositories.CartItemRepository;
import iti.jets.ecommerce.repositories.CustomerRepository;
import iti.jets.ecommerce.repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class CartService {

    private CartItemRepository cartItemRepository;

    private ProductService productService;

    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public CartService(CartItemRepository cartItemRepository, ProductService productService,CustomerRepository customerRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.customerRepository = customerRepository;
    }


    public boolean checkCart(HttpSession session) {
        boolean result = false;
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        if (cart != null) {

            Iterator<CartItemDTO> iterator = cart.iterator();

            while (iterator.hasNext()) {
                CartItemDTO cartItem = iterator.next();
                ProductDTO product = productService.getProductById(cartItem.getProduct().getId());
                if (product == null || product.getQuantity() == 0) {
                    result = true;
                    iterator.remove();
                } else {
                    if (!cartItem.getProduct().equals(product)) {
                        if (product.getQuantity() < cartItem.getQuantity()) {
                            cartItem.setQuantity(product.getQuantity());
                            result = true;
                        } else if (product.getPrice() != cartItem.getProduct().getPrice()) {
                            result = true;
                        }
                        cartItem.setProduct(product);
                    }

                }
            }
        }
        session.setAttribute("cart", cart);
        return result;
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
        checkCart(session);
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

        checkCart(session);
        List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cart");

        if (cartItems != null && !cartItems.isEmpty()) {
            // Convert cartItems to a JSON string
            String cartJson = convertCartToJson(cartItems);
            String base64Cart = Base64.getEncoder().encodeToString(cartJson.getBytes(StandardCharsets.UTF_8)); // Encode to Base64

            // Create a cookie to store the cart data
            Cookie cartCookie = new Cookie("cart", base64Cart);
            cartCookie.setMaxAge(60 * 60 * 24 * 7); // Cookie valid for 7 days
            cartCookie.setPath("/"); // Set the path to ensure the cookie is available throughout the app
            cartCookie.setHttpOnly(false);
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
                    cartJson = new String(Base64.getDecoder().decode(cartJson), StandardCharsets.UTF_8);
                    List<CartItemDTO> cart = convertJsonToCart(cartJson);
                    session.setAttribute("cart", cart);
                    checkCart(session);
                    return cart;
                }
            }
        }
        return new ArrayList<>();
    }



    public  void saveCart(List<CartItemDTO> cart,String username)
    {
        if (cart != null && !cart.isEmpty()) {
            Customer customer = customerRepository.findByUsername(username).get();
            List<CartItem> cartItems = CartItemMapper.toEntity(cart,customer,productRepository);
            System.out.println("inn"+cartItems);
            try{
                cartItemRepository.saveAll(cartItems);
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        }

    }

    @Transactional
    public  List<CartItemDTO> loadCart(String username)
    {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if(customer.isPresent())
        {
            List<CartItem> cartItems = cartItemRepository.findByCustomer(customer.get());
            return CartItemMapper.toDto(cartItems);
        }
        return null;
    }

    @Transactional
    public  void resetCart(String username)
    {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if(customer.isPresent())
        {
            cartItemRepository.deleteAllByCustomer(customer.get());
        }
    }
}
