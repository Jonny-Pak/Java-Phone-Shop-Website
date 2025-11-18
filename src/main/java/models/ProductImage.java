package models;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_images")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(length = 500, nullable = false)
    private String imageUrl;

    // Constructors (Hàm khởi tạo)
    public ProductImage() {}

    public ProductImage(String imageUrl) { this.imageUrl = imageUrl; }

    public Integer getImageId() { return imageId; }
    public void setImageId(Integer imageId) { this.imageId = imageId; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductImage)) return false;
        ProductImage pi = (ProductImage) o;
        return Objects.equals(imageId, pi.imageId);
    }

    @Override
    public int hashCode() { return Objects.hash(imageId); }

    
    // output console
    @Override
    public String toString() {
        return "ProductImage{" + "imageId=" + imageId + ", imageUrl='" + imageUrl + '\'' + '}';
    }
}
