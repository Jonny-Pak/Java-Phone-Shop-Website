package phoneSales.controllers;

import phoneSales.models.Product;
import phoneSales.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/detail/{id}")
    public String viewProductDetail(@PathVariable("id") Integer id, Model model) {
        // lấy sản phẩm theo ID
        Product product = productService.getProductById(id);

        // Đẩy sản phẩm sang giao diện
        model.addAttribute("product", product);

        return "product/product-detail";
    }
}