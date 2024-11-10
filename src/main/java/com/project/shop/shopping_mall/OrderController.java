package com.project.shop.shopping_mall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public String createOrder(@RequestParam Long customerId, @RequestParam Long productId, @RequestParam Integer quantity) {
        orderService.createOrder(customerId, productId, quantity);
        return "redirect:/orders/" + customerId; // 주문 생성 후 주문 목록 페이지로 리다이렉트
    }

    @GetMapping("/{customerId}")
    public String viewOrders(@PathVariable Long customerId, Model model) {
        model.addAttribute("orders", orderService.getOrderById(customerId));
        return "order-list"; // order-list.html
    }
}