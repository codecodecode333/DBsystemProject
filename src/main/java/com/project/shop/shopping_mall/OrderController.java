package com.project.shop.shopping_mall;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @PostMapping("/create")
    public String createOrder(HttpSession session) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // 로그인되지 않았다면 로그인 페이지로 리다이렉트
        }
        // 장바구니에서 사용자가 선택한 상품을 가져오기
        List<Cart> cartItems = cartService.getCartByCustomerId(loggedInUser.getCustomerId());
        if (cartItems.isEmpty()) {
            return "redirect:/cart"; // 장바구니가 비어있으면 장바구니 페이지로 리다이렉트
        }

        for (Cart cartItem : cartItems) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();
            orderService.createOrder(loggedInUser.getCustomerId(), product.getProductId(), quantity); // 주문 생성
        }
        cartService.clearCart(loggedInUser.getCustomerId());

        return "redirect:/orders/view"; // 주문 생성 후 주문 목록 페이지로 리다이렉트
    }

    @PostMapping("/remove")
    public String removeFromCart(RedirectAttributes redirectAttributes, @RequestParam Long orderId) {
        try {
            orderService.removeOrder(orderId); // 주문 삭제 로직 호출
            redirectAttributes.addFlashAttribute("message", "주문이 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "주문 삭제에 실패했습니다: " + e.getMessage());
        }
        return "redirect:/orders/view"; // 장바구니 페이지로 리다이렉트
    }

    

    @GetMapping("/view")
    public String viewOrders(HttpSession session, Model model) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("orders", orderService.getOrdersByCustomer(loggedInUser));
        return "order-list"; // order-list.html
    }
}