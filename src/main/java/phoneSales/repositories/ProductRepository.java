package phoneSales.repositories;

import phoneSales.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Tìm sản phẩm theo tên (tìm kiếm tương đối, không phân biệt hoa thường)
    List<Product> findByProductNameContainingIgnoreCase(String keyword);

    // Lấy danh sách sản phẩm thuộc 1 danh mục
    List<Product> findByCategoryCategoryId(Integer categoryId);

    // Lấy tất cả sản phẩm đang hoạt động (is_active = 1)
    List<Product> findByIsActiveTrue();
}