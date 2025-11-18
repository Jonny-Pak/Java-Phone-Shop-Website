package models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private Double totalAmount;

	@Column(columnDefinition = "NVARCHAR(MAX)", nullable = false)
	private String shippingAddress;

	@Column(length = 50, nullable = false)
	private String status = "Pending";

	private LocalDateTime orderDate = LocalDateTime.now();
	private LocalDateTime expectedDelivery;

	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime updatedAt = LocalDateTime.now();

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderDetail> orderDetails = new ArrayList<>();

	public Order() {
	}

	public Order(User user, String shippingAddress) {
		this.user = user;
		this.shippingAddress = shippingAddress;
		this.totalAmount = 0.0;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getExpectedDelivery() {
		return expectedDelivery;
	}

	public void setExpectedDelivery(LocalDateTime expectedDelivery) {
		this.expectedDelivery = expectedDelivery;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public void addOrderDetail(OrderDetail detail) {
		orderDetails.add(detail);
		detail.setOrder(this);
	}

	public void removeOrderDetail(OrderDetail detail) {
		orderDetails.remove(detail);
		detail.setOrder(null);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Order))
			return false;
		Order or = (Order) o;
		return Objects.equals(orderId, or.orderId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId);
	}

	@Override
	public String toString() {
		return "Order{" + "orderId=" + orderId + ", totalAmount=" + totalAmount + ", status='" + status + '\'' + '}';
	}
}
