package phoneSales.controllers;

import phoneSales.services.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Xem giỏ hàng
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        model.addAttribute("cartItems", cartService.getCart(session));
        model.addAttribute("totalPrice", cartService.getTotalPrice(session));
        return "carts/cart"; 
    }

    // Thêm vào giỏ hàng
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Integer productId, 
                            @RequestParam("quantity") Integer quantity,
                            HttpSession session) {
        cartService.addToCart(session, productId, quantity);
        return "redirect:/cart"; 
    }

    // Xóa sản phẩm
    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable("id") Integer id, HttpSession session) {
        cartService.removeFromCart(session, id);
        return "redirect:/cart";
    }
    
    // Cập nhật số lượng 
    @PostMapping("/update")
    public String updateCart(@RequestParam("productId") Integer productId, 
                             @RequestParam("quantity") Integer quantity,
                             HttpSession session) {
        cartService.updateQuantity(session, productId, quantity);
        return "redirect:/cart";
    }
}