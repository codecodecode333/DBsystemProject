package com.project.shop.shopping_mall;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByCustomer(Customer customer);
    void deleteByCustomer(Customer customer);
}
