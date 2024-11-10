package com.project.shop.shopping_mall;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  CustomerRepository extends JpaRepository<Customer, Long> {
    
}
