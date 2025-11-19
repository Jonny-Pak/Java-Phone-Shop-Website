package phoneSales.repositories;

import phoneSales.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Lấy lịch sử đơn hàng của 1 user
    List<Order> findByUserUserIdOrderByOrderDateDesc(Integer userId);
}