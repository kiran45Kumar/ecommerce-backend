package com.ecommerce.backend.service;
import com.ecommerce.backend.model.*;
import com.ecommerce.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    public Cart addToCart(String email, Long productId, int quantity) {

        Cart cart = cartRepository.findByUserEmail(email)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserEmail(email);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
 
        if (quantity > product.getStock()) {
            throw new RuntimeException("Requested quantity exceeds available stock");
        }
        List<CartItem> items = cartItemRepository.findByCart(cart);

        for (CartItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                 int totalQuantity = item.getQuantity() + quantity;
                 if (totalQuantity > product.getStock()) {
                    throw new RuntimeException("Not enough stock available");
                 }
                item.setQuantity(item.getQuantity() + quantity);
                return cartRepository.save(cart);
            }
        }

        CartItem newItem = new CartItem();
        newItem.setProduct(product);
        newItem.setCart(cart);
        newItem.setQuantity(quantity);

        cartItemRepository.save(newItem);

        return cart;
    }
    public Map<String, Object> getCart(String email) {

    Cart cart = cartRepository.findByUserEmail(email)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    List<CartItem> items = cartItemRepository.findByCart(cart);

    List<Map<String, Object>> itemList = new ArrayList<>();
    double totalAmount = 0;

    for (CartItem item : items) {

        double total = item.getProduct().getPrice() * item.getQuantity();
        totalAmount += total;

        Map<String, Object> map = new HashMap<>();
        map.put("productName", item.getProduct().getName());
        map.put("price", item.getProduct().getPrice());
        map.put("quantity", item.getQuantity());
        map.put("total", total);

        itemList.add(map);
    }

    Map<String, Object> response = new HashMap<>();
    response.put("items", itemList);
    response.put("totalAmount", totalAmount);

    return response;
}

}
