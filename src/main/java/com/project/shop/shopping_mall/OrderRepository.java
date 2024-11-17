package com.project.shop.shopping_mall;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByCustomer(Customer customer);
    // 고객과 연결된 모든 주문 삭제
    void deleteByCustomer(Customer customer);

    // 고객의 ID를 기준으로 주문 삭제
}
