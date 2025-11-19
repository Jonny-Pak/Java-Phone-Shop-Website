package phoneSales.repositories;

import phoneSales.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    // Lấy tất cả sản phẩm trong giỏ hàng
    List<CartItem> findByCartCartId(Integer cartId);
    
    // Xóa giỏ hàng sau khi đặt hàng thành công
    void deleteByCartCartId(Integer cartId);
}