package com.project.shop.shopping_mall;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Cart addToCart(Long customerId, Long productId, int quantity) {
        // 고객과 상품을 각각 ID로 조회
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        // Cart 객체 생성 및 설정
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setProduct(product);
        cart.setQuantity(quantity);

        // 장바구니 저장
        return cartRepository.save(cart);
    }

    public List<Cart> getCartItems(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));
        return cartRepository.findByCustomer(customer);
    }

    @Transactional
    public void clearCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));
        // Customer 객체를 사용하여 Cart 항목 삭제
        cartRepository.deleteByCustomer(customer);
    }

    public List<Cart> getCartByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));
        List<Cart> cart = cartRepository.findByCustomer(customer);
        return cart;
    }

    public int calculateTotalPrice(Long customerId) {
        // 장바구니 항목 가져오기
        List<Cart> cartItems = getCartByCustomerId(customerId);

        // 각 항목의 가격 * 수량을 합산
        return cartItems.stream()
                .mapToInt(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public void removeCartItem(Long cartId) {
        Cart cartItem = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다."));
        
        cartRepository.delete(cartItem); // 고유 CartItemId로 삭제
    }
}