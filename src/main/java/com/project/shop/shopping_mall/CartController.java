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
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public String addProductToCart(@RequestParam Long customerId, @RequestParam Long productId, @RequestParam Integer quantity) {
        cartService.addToCart(customerId, productId, quantity);
        return "redirect:/cart/" + customerId; // 장바구니 페이지로 리다이렉트
    }

    @GetMapping("/{customerId}")
    public String viewCart(@PathVariable Long customerId, Model model) {
        model.addAttribute("cart", cartService.getCartByCustomerId(customerId));
        return "cart"; // cart.html
    }
}
