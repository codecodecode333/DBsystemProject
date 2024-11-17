package com.project.shop.shopping_mall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;

    

    // 주문 생성 (상품과 고객 정보를 기반으로 주문 생성)
    @Transactional
    public Orders createOrder(Long customerId, Long productId, Integer quantity) {
        // 고객 및 상품 유효성 검사
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        Optional<Product> productOptional = productRepository.findById(productId);
    
        if (!customerOptional.isPresent() || !productOptional.isPresent()) {
            throw new IllegalArgumentException("유효한 고객 또는 상품이 아닙니다.");
        }
    
        Customer customer = customerOptional.get();
        Product product = productOptional.get();
    
        // 재고 확인
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다. 남은 재고: " + product.getStock());
        }
    
        // 주문 생성
        Orders order = new Orders();
        order.setCustomer(customer);
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setOrderDate(java.time.LocalDateTime.now());
        order.setOrderStatus("주문완료");  // 기본 상태 설정 (주문완료 등)
    
        // 재고 감소 처리
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);  // 상품 재고 업데이트
    
        return orderRepository.save(order);  // 주문 저장
    }

    // 고객 ID로 모든 주문 조회
    public List<Orders> getOrdersByCustomer(Customer customer) {
        return orderRepository.findByCustomer(customer);  // 고객의 모든 주문 조회
    }

    // 주문 ID로 주문 조회
    public Orders getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
    }

    // 주문 상태 업데이트
    @Transactional
    public Orders updateOrderStatus(Long orderId, String newStatus) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
        order.setOrderStatus(newStatus);
        return orderRepository.save(order);  // 주문 상태 업데이트
    }

    // 고객의 주문을 모두 삭제
    @Transactional
    public void deleteOrdersByCustomer(Customer customer) {
        List<Orders> orders = orderRepository.findByCustomer(customer);
        orderRepository.deleteAll(orders);  // 해당 고객의 모든 주문 삭제
    }

    public void removeOrder(Long orderId) {
        // 주문 조회
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
        
        // 주문 삭제
        orderRepository.delete(order);
    }

}
