package config;

import models.*;
import repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(CategoryRepository categoryRepo,
                           ProductRepository productRepo,
                           UserRepository userRepo,
                           ProductImageRepository imageRepo) {
        return args -> {
            if (categoryRepo.count() == 0) {
                Category c1 = new Category();
                c1.setCategoryName("Smartphone");
                c1.setDescription("Điện thoại thông minh phổ biến");
                categoryRepo.save(c1);

                Category c2 = new Category();
                c2.setCategoryName("Accessories");
                c2.setDescription("Phụ kiện");
                categoryRepo.save(c2);

                Product p1 = new Product();
                p1.setProductName("Phone A");
                p1.setBrand("BrandA");
                p1.setModel("A1");
                p1.setPrice(499.99);
                p1.setStockQuantity(50);
                p1.setDescription("Phone A - cấu hình tốt, giá hợp lý");
                p1.setSpecifications("Ram 6GB, Rom 128GB");
                p1.setCategory(c1);
                productRepo.save(p1);

                Product p2 = new Product();
                p2.setProductName("Phone B");
                p2.setBrand("BrandB");
                p2.setModel("B1");
                p2.setPrice(299.99);
                p2.setStockQuantity(30);
                p2.setDescription("Phone B - ổn định cho học sinh");
                p2.setSpecifications("Ram 4GB, Rom 64GB");
                p2.setCategory(c1);
                productRepo.save(p2);

                // ảnh mẫu (chỉ URL giả, bạn có thể upload thật later)
                ProductImage img1 = new ProductImage();
                img1.setProduct(p1);
                img1.setImageUrl("/images/sample-phone-a.jpg");
                imageRepo.save(img1);

                ProductImage img2 = new ProductImage();
                img2.setProduct(p2);
                img2.setImageUrl("/images/sample-phone-b.jpg");
                imageRepo.save(img2);
            }

            if (!userRepo.existsByEmail("admin@demo.com")) {
                User admin = new User();
                admin.setFullName("Admin Demo");
                admin.setEmail("admin@demo.com");
                String encoded = new BCryptPasswordEncoder().encode("admin123");
                admin.setPassword(encoded);
                admin.setRole("ADMIN");
                userRepo.save(admin);
            }
        };
    }
}
