package phoneSales.services;

import phoneSales.models.CartItemDto;
import phoneSales.models.Product;
import phoneSales.repositories.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;

    private static final String SESSION_CART_KEY = "cart";

    // 1. Lấy giỏ hàng từ Session
    public List<CartItemDto> getCart(HttpSession session) {
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute(SESSION_CART_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(SESSION_CART_KEY, cart);
        }
        return cart;
    }

    // 2. Thêm sản phẩm vào giỏ hàng
    public void addToCart(HttpSession session, Integer productId, Integer quantity) {
        List<CartItemDto> cart = getCart(session);
        
        // Kiểm tra xem sản phẩm đã có trong giỏ chưa
        boolean exists = false;
        for (CartItemDto item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                exists = true;
                break;
            }
        }

        // Nếu chưa có, lấy thông tin từ DB và thêm mới
        if (!exists) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                // Lấy ảnh đầu tiên, nếu không có thì dùng ảnh rỗng
                String image = (product.getProductImages() != null && !product.getProductImages().isEmpty()) 
                                ? product.getProductImages().get(0).getImageUrl() 
                                : "";
                                
                CartItemDto newItem = new CartItemDto(
                        product.getProductId(),
                        product.getProductName(),
                        image,
                        quantity,
                        product.getPrice().doubleValue()
                );
                cart.add(newItem);
            }
        }
        session.setAttribute(SESSION_CART_KEY, cart);
    }

    // 3. Cập nhật số lượng sản phẩm trong giỏ hàng
    public void updateQuantity(HttpSession session, Integer productId, Integer quantity) {
        List<CartItemDto> cart = getCart(session);
        for (CartItemDto item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
                break;
            }
        }
        session.setAttribute(SESSION_CART_KEY, cart);
    }

    // 4. Xóa sản phẩm khỏi giỏ hàng
    public void removeFromCart(HttpSession session, Integer productId) {
        List<CartItemDto> cart = getCart(session);
        cart.removeIf(item -> item.getProductId().equals(productId));
        session.setAttribute(SESSION_CART_KEY, cart);
    }
    
    // 5. Tính tổng tiền
    public double getTotalPrice(HttpSession session) {
        List<CartItemDto> cart = getCart(session);
        return cart.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }
}