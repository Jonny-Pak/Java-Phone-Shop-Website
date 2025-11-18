package services;

import models.Cart;
import models.CartItem;
import models.Product;
import models.User;
import repositories.CartItemRepository;
import repositories.CartRepository;
import repositories.ProductRepository;
import repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
			ProductRepository productRepository, UserRepository userRepository) {
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	// Lấy giỏ của người dùng, nếu chưa có thì tạo mới
	public Cart createCartForUser(Integer userId) {
		if (userId == null) {
			throw new IllegalArgumentException("Cần có userid để thực hiện tác vụ này");
		}
		Optional<Cart> maybeCart = cartRepository.findByUserUserId(userId);
		if (maybeCart.isPresent()) {
			return maybeCart.get();
		}

		// create new cart
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng có ID: " + userId));
		Cart cart = new Cart();
		cart.setUser(user);
		return cartRepository.save(cart);
	}

	// Lấy giỏ hàng (nếu có), trả về Optional.
	@Transactional(readOnly = true)
	public Optional<Cart> getCartForUser(Integer userId) {
		if (userId == null) {
			return Optional.empty();
		}
		return cartRepository.findByUserUserId(userId);
	}

	// Thêm sản phẩm vào giỏ hàng
	// Nếu sản phẩm đã tồn tại trong giỏ thì cộng dồn số lượng sản phẩm trong giỏ
	// hảng
	public void addToCart(Integer userId, Integer productId, int qty) {
		if (userId == null || productId == null) {
			throw new IllegalArgumentException("Cần phải có userId và productId để thực hiện tác vụ này");
		}
		if (qty <= 0) {
			throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
		}

		Cart cart = createCartForUser(userId);

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + productId));

		Optional<CartItem> maybeItem = cartItemRepository.findByCartCartIdAndProductProductId(cart.getCartId(),
				productId);

		if (maybeItem.isPresent()) {
			CartItem item = maybeItem.get();
			item.setQuantity(item.getQuantity() + qty);
			cartItemRepository.save(item);
		} else {
			CartItem newItem = new CartItem();
			newItem.setCart(cart);
			newItem.setProduct(product);
			newItem.setQuantity(qty);
			cartItemRepository.save(newItem);
		}

		// cập nhật giỏ hàng và cập nhật thời gian thêm giỏ hàng
		cart.setUpdatedAt(java.time.LocalDateTime.now());
		cartRepository.save(cart);
	}

	// Cập nhật số lượng của item trong giỏ hàng
	public void updateItemQuantity(Integer userId, Integer productId, int qty) {
		if (userId == null || productId == null) {
			throw new IllegalArgumentException("Cần phải có userId và productId để thực hiện tác vụ này");
		}
		// nếu số lượng sản phẩm trong giỏ hàng bé hơn 0 thì lập tức xóa sản phẩm khỏi
		// giỏ hàng
		if (qty <= 0) {
			removeItemFromCart(userId, productId);
			return;
		}
		Cart cart = cartRepository.findByUserUserId(userId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng của người dùng: " + userId));
		CartItem item = cartItemRepository.findByCartCartIdAndProductProductId(cart.getCartId(), productId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));
		item.setQuantity(qty);
		cartItemRepository.save(item);
		cart.setUpdatedAt(java.time.LocalDateTime.now());
		cartRepository.save(cart);
	}

	// Xóa item khỏi giỏ hàng.

	public void removeItemFromCart(Integer userId, Integer productId) {
		if (userId == null || productId == null) {
			throw new IllegalArgumentException("Cần phải có userId và productId để thực hiện tác vụ này!");
		}
		Cart cart = cartRepository.findByUserUserId(userId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng của người dùng " + userId));
		Optional<CartItem> maybeItem = cartItemRepository.findByCartCartIdAndProductProductId(cart.getCartId(),
				productId);
		maybeItem.ifPresent(cartItemRepository::delete);
		cart.setUpdatedAt(java.time.LocalDateTime.now());
		cartRepository.save(cart);
	}

	// Xóa giỏ hàng
	public void clearCart(Integer userId) {
		Cart cart = cartRepository.findByUserUserId(userId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng của người dùng " + userId));
		List<CartItem> items = cartItemRepository.findByCartCartId(cart.getCartId());
		cartItemRepository.deleteAll(items);
		cart.setUpdatedAt(java.time.LocalDateTime.now());
		cartRepository.save(cart);
	}
}
