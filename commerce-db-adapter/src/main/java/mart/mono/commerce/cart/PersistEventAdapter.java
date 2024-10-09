package mart.mono.commerce.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import mart.mono.inventory.event.EventType;
import mart.mono.inventory.event.ProductAddedToCartEvent;
import mart.mono.inventory.event.ProductRemovedFromCartEvent;
import mart.mono.inventory.event.PurchaseEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class PersistEventAdapter {

    private final EventEntityRepository eventEntityRepository;
    private final ObjectMapper objectMapper;

    @Async
    @EventListener
    @SneakyThrows
    public void save(ProductAddedToCartEvent productAddedToCartEvent) {
        EventEntity productChangedEventEntity = EventEntity.builder()
                .sessionId(productAddedToCartEvent.getSessionId())
                .eventType(EventType.PRODUCT_ADDED_TO_CART)
                .serializedEvent(objectMapper.writeValueAsString(productAddedToCartEvent))
                .publicationDate(ZonedDateTime.now())
                .build();
        eventEntityRepository.save(productChangedEventEntity);
    }

    @Async
    @EventListener
    @SneakyThrows
    public void remove(ProductRemovedFromCartEvent productRemovedFromCartEvent) {
        EventEntity productChangedEventEntity = EventEntity.builder()
                .sessionId(productRemovedFromCartEvent.getSessionId())
                .eventType(EventType.PRODUCT_REMOVED_FROM_CART)
                .serializedEvent(objectMapper.writeValueAsString(productRemovedFromCartEvent))
                .publicationDate(ZonedDateTime.now())
                .build();
        eventEntityRepository.save(productChangedEventEntity);
    }

    @Async
    @EventListener
    @SneakyThrows
    public void purchase(PurchaseEvent purchaseEvent) {
        EventEntity productChangedEventEntity = EventEntity.builder()
                .sessionId(purchaseEvent.getSessionId())
                .eventType(EventType.PRODUCTS_PURCHASED)
                .serializedEvent(objectMapper.writeValueAsString(purchaseEvent))
                .publicationDate(ZonedDateTime.now())
                .build();
        eventEntityRepository.save(productChangedEventEntity);
    }
}
