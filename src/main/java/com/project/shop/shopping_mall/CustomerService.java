package com.project.shop.shopping_mall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    //id로 고객 반환
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));
    }

    //고객 등록
    public Customer createCustomer(String name, String email, String password) {
        Customer customer = new Customer(null, name, email, password, null, null);
        customer.setName(name);

        return customerRepository.save(customer);
    }

    //고객 수정
    public Customer updateCustomer(Long customerId, Customer customer) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer not found with id: " + customerId);
        }
        customer.setCustomerId(customerId);  // 업데이트할 고객 ID를 설정
        return customerRepository.save(customer);
    }

    //고객 삭제
    public void deleteCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer not found with id: " + customerId);
        }
        customerRepository.deleteById(customerId);
    }

    //장바구니 조회
    public List<Cart> getCartsByCustomerId(Long customerId) {
        Customer customer = getCustomerById(customerId);
        return cartRepository.findByCustomer(customer);  // 장바구니 목록 조회
    }

    //주문목록조회
    public List<Orders> getOrdersByCustomerId(Long customerId) {
        Customer customer = getCustomerById(customerId);  // 고객 정보 확인
        return orderRepository.findByCustomer(customer);  // 주문 목록 조회
    }

    //장바구니 삭제
    public void deleteCartsByCustomerId(Long customerId) {
        Customer customer = getCustomerById(customerId);
        cartRepository.deleteByCustomer(customer);  // 고객과 연결된 장바구니 삭제
    }

    //주문삭제
    public void deleteOrdersByCustomerId(Long customerId) {
        Customer customer = getCustomerById(customerId);  // 고객 정보 확인
        orderRepository.deleteByCustomer(customer);  // 고객과 연결된 주문 삭제
    }
    //email로 찾기
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}