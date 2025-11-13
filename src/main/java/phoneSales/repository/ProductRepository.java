package phoneSales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phoneSales.entity.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryId(Integer categoryId);
    List<Product> findByBrandContainingIgnoreCase(String brand);
}
