package repositories;

import models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCartCartIdAndProductProductId(Integer cartId, Integer productId);
    List<CartItem> findByCartCartId(Integer cartId);
}
