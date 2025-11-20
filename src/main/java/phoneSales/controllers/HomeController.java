package phoneSales.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import phoneSales.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    // Trang chủ
    @GetMapping("/") 
    public String home(Model model) {
        model.addAttribute("products", productService.getAllActiveProducts());
       
        return "index"; 
    }
}