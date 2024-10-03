package mart.mono.commerce.purchase;

import lombok.RequiredArgsConstructor;
import mart.mono.inventory.lib.PurchaseEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseEventPublisherAdapter implements PurchaseEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void productPurchased(UUID productId, Integer quantity) {
        eventPublisher.publishEvent(PurchaseEvent.builder().productId(productId).quantity(quantity).build());
    }
}
