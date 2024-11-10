package com.project.shop.shopping_mall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        // 예시로 상품 목록을 가져오는 서비스 호출
        model.addAttribute("products", productService.getAllProducts());
        return "home"; // home.html
    }
}
