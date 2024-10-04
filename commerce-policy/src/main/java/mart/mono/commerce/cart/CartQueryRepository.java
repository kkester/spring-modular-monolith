package mart.mono.commerce.cart;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartQueryRepository {
    List<CartItem> findAll();
    Optional<CartItem> findById(UUID cartItemId);
}
