package com.project.shop.shopping_mall;

import java.time.LocalDateTime;
//  import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId; // 주문 ID

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer; // 고객 참조

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 상품 참조

    private Integer quantity; // 상품 수량

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate; // 주문 날짜

    @Column(name = "order_status", nullable = false)
    private String orderStatus; // 주문 상태
}
