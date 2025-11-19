package phoneSales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// --- QUAN TRỌNG: Thêm dòng exclude bên dưới để tắt màn hình đăng nhập ---
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PhoneSalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoneSalesApplication.class, args);
    }

}