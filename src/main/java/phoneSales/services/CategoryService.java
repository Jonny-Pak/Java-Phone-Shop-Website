package phoneSales.services;

import phoneSales.models.Category;
import phoneSales.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy tất cả danh mục
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    // Thêm/Sửa danh mục (Admin)
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }
    
    // Xóa danh mục  (Admin)
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}