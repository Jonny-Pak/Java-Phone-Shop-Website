package services;

import models.Product;
import repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

	private final ProductRepository productRepository;

	// constructor injection (tốt cho test)
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	// Lấy danh sách sản phẩm đang được bán (isActive = true).
	public List<Product> listActiveProducts() {
		return productRepository.findByIsActiveTrue();
	}

	// Lấy tất cả sản phẩm (Admin).
	public List<Product> listAllProducts() {
		return productRepository.findAll();
	}

	// Tìm sản phẩm theo id
	public Optional<Product> findById(Integer id) {
		if (id == null) {
			return Optional.empty();
		}
		return productRepository.findById(id);
	}

	// Lưu hoặc cập nhật sản phẩm. (Gọi method trong transaction write: cho phép ghi
	// dữ liệu vào database)
	@Transactional
	public Product save(Product product) {
		return productRepository.save(product);
	}

	// Xóa product theo id.
	@Transactional
	public void deleteById(Integer id) {
		productRepository.deleteById(id);
	}

	// Lấy theo sản phẩm theo danh mục (nếu cần lọc).
	public List<Product> findByCategoryId(Integer categoryId) {
		if (categoryId == null) {
			return List.of();
		}
		return productRepository.findByCategoryCategoryId(categoryId);
	}
}
