package phoneSales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Cho phép truy cập tài nguyên và các trang ở dạng public
                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/vendor/**").permitAll()
                .requestMatchers("/", "/home", "/register", "/login", "/product/**", "/cart/**").permitAll()
                
                // Trang thanh toán và đơn hàng (cần đăng nhập) 
                .requestMatchers("/checkout", "/place-order").authenticated()
                
                // Trang Admin 
                // .requestMatchers("/admin/**").hasRole("ADMIN")
                
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") 
                .loginProcessingUrl("/login") 
                .defaultSuccessUrl("/") // Đăng nhập thành công về trang chủ
                .failureUrl("/login?error=true") // Sai pass thì về lại trang login kèm lỗi
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        return http.build();
    }

    // Mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}