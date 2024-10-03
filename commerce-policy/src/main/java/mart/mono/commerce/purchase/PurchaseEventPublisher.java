package mart.mono.commerce.purchase;

import java.util.UUID;

public interface PurchaseEventPublisher {
    void productPurchased(UUID productId, Integer quantity);
}
