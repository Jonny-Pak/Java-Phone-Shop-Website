package phoneSales.services;

import phoneSales.models.*;
import phoneSales.repositories.*;
import jakarta.servlet.http.HttpSession;
import phoneSales.models.CartItemDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional // Đảm bảo nếu có lỗi thì rollback (không lưu dở dang)
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;

    // Hàm xử lý đặt hàng
    public void placeOrder(String fullName, String email, String phone, String address, String note, HttpSession session) {
        // 1. Lấy giỏ hàng
        List<CartItemDto> cartItems = cartService.getCart(session);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống!");
        }

        // 2. Xử lý User (Khách vãng lai)
        // Kiểm tra xem email này đã từng mua hàng chưa
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            // Nếu chưa có, tạo tài khoản mới cho khách
            user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAddress(address);
            user.setPassword("123456"); // Mật khẩu mặc định
            user.setRole("CUSTOMER");
            user.setIsActive(true);
            user = userRepository.save(user);
        } else {
            // Nếu có rồi thì cập nhật lại thông tin mới nhất
            user.setFullName(fullName);
            user.setPhone(phone);
            user.setAddress(address);
            userRepository.save(user);
        }

        // 3. Tạo Order (Đơn hàng)
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(address);
        order.setStatus("Pending"); // Chờ xử lý
        order.setTotalAmount(BigDecimal.valueOf(cartService.getTotalPrice(session)));
        
        // Lưu Order trước để có ID
        Order savedOrder = orderRepository.save(order);

        // 4. Tạo Order Details (Chi tiết đơn hàng)
        for (CartItemDto item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(savedOrder);
            
            // Lưu ý: Chỗ này cần lấy Product entity từ DB nếu muốn chuẩn xác hơn
            // Nhưng để nhanh, ta set ID product thông qua constructor hoặc setter của Product
            Product product = new Product(); 
            product.setProductId(item.getProductId());
            detail.setProduct(product);
            
            detail.setQuantity(item.getQuantity());
            detail.setUnitPrice(BigDecimal.valueOf(item.getPrice()));
            detail.setTotal(BigDecimal.valueOf(item.getPrice() * item.getQuantity()));
            
            orderDetailRepository.save(detail);
        }

        // 5. Xóa giỏ hàng sau khi đặt thành công
        session.removeAttribute("cart");
    }
}