package phoneSales.controllers;

import phoneSales.models.User;
import phoneSales.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Trang đăng nhập
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login"; 
    }

    // Trang đăng ký
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // Xử lý quy trình đăng ký đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            userService.registerUser(user);
            return "redirect:/login?success=true"; // Đăng ký xong chuyển qua trang login
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register"; // Lỗi thì ở lại trang đăng ký
        }
    }
}