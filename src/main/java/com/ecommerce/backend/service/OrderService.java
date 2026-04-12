package com.ecommerce.backend.service;

import com.ecommerce.backend.model.*;
import com.ecommerce.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public Order placeOrder(String email) {

        Cart cart = cartRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> items = cartItemRepository.findByCart(cart);

        if (items.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double totalAmount = 0;

        Order order = new Order();
        order.setUserEmail(email);

        order = orderRepository.save(order);

        for (CartItem item : items) {

            Product product = item.getProduct();

            // 🔥 Check stock again
            if (item.getQuantity() > product.getStock()) {
                throw new RuntimeException("Stock changed, try again");
            }

            // 🔥 Reduce stock
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            double total = product.getPrice() * item.getQuantity();
            totalAmount += total;

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItemRepository.save(orderItem);
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        cartItemRepository.deleteAll(items);

        return order;
    }
    public List<Order> getUserOrders(String email) {
        return orderRepository.findByUserEmail(email);
    }
    public List<OrderItem> getOrderItems(Long orderId) {
    return orderItemRepository.findByOrderId(orderId);
}
}