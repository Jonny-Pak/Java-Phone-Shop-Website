package phoneSales.services;

import phoneSales.models.User;
import phoneSales.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SecurityConfigService implements UserDetailsService {
	//implements khác với extend
	//implements là dùng giữa class với interface còn extend là lớp con kế thừa lớp cha

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Tìm user trong bằng email
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user với email: " + email));

        // Trả về đối tượng User của Spring Security 
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}