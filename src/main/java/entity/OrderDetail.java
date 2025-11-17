package entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_details", uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "product_id"}))
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity = 1;

    @Column(nullable = false, precision = 12, scale = 2)
    private Double unitPrice;

    @Column(nullable = false, precision = 12, scale = 2)
    private Double total;

    public OrderDetail() {}

    public OrderDetail(Product product, Integer quantity, Double unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = unitPrice * quantity;
    }

    public Integer getOrderDetailId() { return orderDetailId; }
    public void setOrderDetailId(Integer orderDetailId) { this.orderDetailId = orderDetailId; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        recalcTotal();
    }

    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        recalcTotal();
    }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    private void recalcTotal() {
        if (this.unitPrice != null && this.quantity != null) {
            this.total = this.unitPrice * this.quantity;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetail)) return false;
        OrderDetail od = (OrderDetail) o;
        return Objects.equals(orderDetailId, od.orderDetailId);
    }

    @Override
    public int hashCode() { return Objects.hash(orderDetailId); }

    @Override
    public String toString() {
        return "OrderDetail{" + "orderDetailId=" + orderDetailId + ", product=" + (product != null ? product.getProductId() : null) + ", total=" + total + '}';
    }
}
