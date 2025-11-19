package phoneSales.repositories;

import phoneSales.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    // Lấy chi tiết sản phẩm của 1 đơn hàng
    List<OrderDetail> findByOrderOrderId(Integer orderId);
}