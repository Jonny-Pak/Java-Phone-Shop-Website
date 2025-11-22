package phoneSales.models;

public class CartItemDto {
    private Integer productId;
    private String productName;
    private String imageUrl;
    private Integer quantity;
    private Double price;

    // Constructor rỗng
    public CartItemDto() {}

    // Constructor có tham số
    public CartItemDto(Integer productId, String productName, String imageUrl, Integer quantity, Double price) {
        this.productId = productId;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = price;
    }

    // GETTERS VÀ SETTERS 
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}