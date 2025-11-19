package phoneSales.repositories;

import phoneSales.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // JpaRepository đã có sẵn hàm findAll(), findById(), save(), delete()
}