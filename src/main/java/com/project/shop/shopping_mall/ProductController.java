package com.project.shop.shopping_mall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String viewProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product-list"; // product-list.html
    }

    @GetMapping("/{id}")
    public String viewProduct(HttpSession session, @PathVariable Long id, Model model) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("product", productService.getProductById(id));
        return "product-detail"; // product-detail.html
    }
}