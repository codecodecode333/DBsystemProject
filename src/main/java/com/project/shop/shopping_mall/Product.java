package com.project.shop.shopping_mall;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId; // 상품 ID

    private String name; // 상품 이름
    private String description; // 상품 설명
    private Double price; // 상품 가격

    @Column(nullable = false)
    private Integer stock; // 재고 수

    @Column(nullable = false)
    private String category; // 카테고리

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Order> orders; // 주문에서 참조되는 상품 목록
}