package phoneSales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phoneSales.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {}
