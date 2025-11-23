package phoneSales.services;

import phoneSales.models.User;
import phoneSales.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }
        
        // Mã hóa mật khẩu trước khi lưu vào data
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Set mặc định khi có user đăng ký mới
        user.setRole("CUSTOMER");
        user.setIsActive(true);
        
        userRepository.save(user);
    }
}