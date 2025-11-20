package phoneSales.services;

import phoneSales.models.Product;
import phoneSales.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // --- ĐÃ SỬA: Bỏ từ khóa 'static' ---
    // Lấy sản phẩm còn hàng (active = true) để hiển thị trang chủ
    public List<Product> getAllActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }

    // Lấy tất cả sản phẩm (Dùng cho Admin)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Xem chi tiết 1 sản phẩm
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm có ID: " + id));
    }

    // Tìm kiếm sản phẩm
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByProductNameContainingIgnoreCase(keyword);
    }

    // Lọc sản phẩm theo danh mục
    public List<Product> getProductsByCategory(Integer categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId);
    }

    // Lưu sản phẩm (Thêm mới hoặc Cập nhật)
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // Xóa sản phẩm
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}