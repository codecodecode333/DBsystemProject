package com.project.shop.shopping_mall;

//import java.util.List;

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
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne // 여러 장바구니가 하나의 고객에 속함
    @JoinColumn(name = "customer_id", nullable = false) // 외래 키 설정
    private Customer customer;

    @ManyToOne // 여러 장바구니가 하나의 상품에 속함
    @JoinColumn(name = "product_id", nullable = false) // 외래 키 설정
    private Product product; // 상품을 나타내는 필드

    @Column(name = "quantity")
    private Integer quantity; // 상품 수량
}