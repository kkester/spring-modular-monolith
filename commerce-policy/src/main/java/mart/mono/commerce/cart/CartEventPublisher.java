package mart.mono.commerce.cart;

import java.util.UUID;

public interface CartEventPublisher {
    void productAddedToCart(UUID productId, Integer quantity);
    void productRemovedFromCart(CartItem cartItem);
}
