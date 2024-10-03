package mart.mono.commerce.cart;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository {
    List<CartItem> findAll();
    CartItem save(CartItem cartItem);
    Optional<CartItem> findById(UUID cartItemId);
    void delete(CartItem cartItem);
    void deleteAll();
}
