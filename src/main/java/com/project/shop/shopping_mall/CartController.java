package com.project.shop.shopping_mall;

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
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public String addProductToCart(HttpSession session, @RequestParam String productId, @RequestParam Integer quantity, RedirectAttributes redirectAttributes) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        Long productIdLong = Long.parseLong(productId);
    
        // 상품 조회
        Product product = productService.getProductById(productIdLong);
    
        // 재고 확인
        if (product.getStock() < quantity) {
            redirectAttributes.addFlashAttribute("error", "재고가 부족합니다.");
            return "redirect:/products/" + productId; // 상품 상세 페이지로 리다이렉트
        }

        // 장바구니 추가
        cartService.addToCart(loggedInUser.getCustomerId(), productIdLong, quantity);
        return "redirect:/cart/view";
    }

    @PostMapping("/remove")
    public String removeFromCart(HttpSession session,RedirectAttributes redirectAttributes, @RequestParam Long cartId) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        try {
            cartService.removeCartItem(cartId); // 주문 삭제 로직 호출
            redirectAttributes.addFlashAttribute("message", "장바구니 상품이 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "장바구니 상품 삭제에 실패했습니다: " + e.getMessage());
        }

        return "redirect:/cart/view"; // 장바구니 페이지로 리다이렉트
    }

    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {

        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        Long customerId = loggedInUser.getCustomerId();
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("cart", cartService.getCartByCustomerId(customerId));
        model.addAttribute("customerId", customerId);
        model.addAttribute("totalPrice", cartService.calculateTotalPrice(customerId));
        return "cart"; // cart.html
    }
}
