package phoneSales.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phoneSales.models.User;
import phoneSales.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	
	// Đăng ký tài khoản
	public User Register(User user) {
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Email này đã được sử dụng!");
		}
		return userRepository.save(user);
	}
	
	// Đăng nhập tài khoản
	public User Login(String email, String password) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email không tồn tại!"));
		
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("Mật khẩu không đúng!");
		}
		return user;
	}
	
	// Cập nhật thông tin người dùng
	public User update(Integer userId, User userDetails) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        user.setFullName(userDetails.getFullName());
        user.setPhone(userDetails.getPhone());
        user.setAddress(userDetails.getAddress());
        return userRepository.save(user);
    }
}