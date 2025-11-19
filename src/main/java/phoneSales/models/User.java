package phoneSales.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	// Khớp với NVARCHAR(100)
	@Column(name = "full_name", nullable = false, length = 100)
	private String fullName;

	// Khớp với NVARCHAR(100)
	@Column(nullable = false, unique = true, length = 100)
	private String email;

	// Khớp với NVARCHAR(255)
	@Column(nullable = false, length = 255)
	private String password;

	@Column(length = 10)
	private String phone;

	// Khớp với NVARCHAR(MAX)
	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String address;

	// Khớp với NVARCHAR(20) - Quan trọng để tránh lỗi ALTER bảng
	@Column(nullable = false, length = 20)
	private String role;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "is_active")
	private Boolean isActive;

	public User() {
		this.createdAt = LocalDateTime.now();
		this.isActive = true;
		this.role = "CUSTOMER";
	}

	// --- GETTERS AND SETTERS ---

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}