package com.project.shop.shopping_mall;

import java.util.Set;

import jakarta.persistence.CascadeType;
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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId; // 고객 ID

    private String name; // 고객 이름
    private String email; // 고객 이메일
    private String password; // 고객 비밀번호

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Cart> carts; // 고객의 장바구니 목록

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Orders> orders; // 고객의 주문 목록
}