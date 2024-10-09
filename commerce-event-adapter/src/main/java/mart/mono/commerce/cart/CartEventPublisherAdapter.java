package mart.mono.commerce.cart;

import lombok.RequiredArgsConstructor;
import mart.mono.commerce.util.SessionUtil;
import mart.mono.inventory.event.ProductAddedToCartEvent;
import mart.mono.inventory.event.ProductRemovedFromCartEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartEventPublisherAdapter implements CartEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private final SessionUtil sessionUtil;

    @Override
    public void productAddedToCart(UUID productId, Integer quantity) {
        ProductAddedToCartEvent productAddedToCartEvent = ProductAddedToCartEvent.builder()
                .sessionId(sessionUtil.getSessionId())
                .productId(productId)
                .quantity(quantity)
                .build();
        eventPublisher.publishEvent(productAddedToCartEvent);
    }

    @Override
    public void productRemovedFromCart(CartItem cartItem) {
        ProductRemovedFromCartEvent productRemovedFromCartEvent = ProductRemovedFromCartEvent.builder()
                .sessionId(sessionUtil.getSessionId())
                .cartItemId(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .build();
        eventPublisher.publishEvent(productRemovedFromCartEvent);
    }
}
