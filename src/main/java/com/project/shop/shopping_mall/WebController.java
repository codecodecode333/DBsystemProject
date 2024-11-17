package com.project.shop.shopping_mall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login"; // 첫 화면을 로그인 페이지로 설정
    }

   // 회원가입 페이지 표시
   @GetMapping("/signup")
   public String showSignUpForm() {
       return "signup"; // signup.html을 반환
   }

   // 회원가입 처리
   @PostMapping("/signup")
   public String signUp(@RequestParam String name, 
                        @RequestParam String email, 
                        @RequestParam String password) {
       customerService.createCustomer(name, email, password); // 고객 생성
       return "redirect:/login"; // 회원가입 후 로그인 페이지로 리다이렉트
   }

   @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("error", ""); // 초기 상태에 에러 메시지 없음
        return "login"; // login.html을 반환
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String email, 
                        @RequestParam String password,
                        HttpSession session, 
                        Model model) {
        Customer customer = customerService.findByEmail(email); // 이메일로 고객 찾기

        if (customer != null && customer.getPassword().equals(password)) {
            //세션에 저장
            session.setAttribute("loggedInUser", customer);
            // 로그인 성공: 홈 또는 다른 페이지로 리다이렉트
            return "redirect:/home";
        } else {
            // 로그인 실패: 에러 메시지와 함께 로그인 페이지로 다시 이동
            model.addAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }
    }

    @GetMapping("/logout")
        public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("customerId", loggedInUser.getCustomerId());
        return "home";
    }
    
}
