package mart.mono.commerce.purchase;

import lombok.RequiredArgsConstructor;
import mart.mono.commerce.util.SessionUtil;
import mart.mono.inventory.event.PurchaseEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseEventPublisherAdapter implements PurchaseEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private final SessionUtil sessionUtil;

    @Override
    public void productPurchased(UUID productId, Integer quantity) {
        PurchaseEvent purchaseEvent = PurchaseEvent.builder()
                .sessionId(sessionUtil.getSessionId())
                .productId(productId)
                .quantity(quantity)
                .build();
        eventPublisher.publishEvent(purchaseEvent);
    }
}
