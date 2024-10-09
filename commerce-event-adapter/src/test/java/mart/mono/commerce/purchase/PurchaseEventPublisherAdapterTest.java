package mart.mono.commerce.purchase;

import mart.mono.commerce.util.SessionUtil;
import mart.mono.inventory.event.PurchaseEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PurchaseEventPublisherAdapterTest {

    @InjectMocks
     PurchaseEventPublisherAdapter purchaseEventPublisher;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    SessionUtil sessionUtil;

    @Test
    void productPurchasedTest() {
        String sessionId = UUID.randomUUID().toString();
        when(sessionUtil.getSessionId()).thenReturn(sessionId);

        UUID productId = UUID.randomUUID();
        purchaseEventPublisher.productPurchased(productId, 5);

        ArgumentCaptor<PurchaseEvent> captor = ArgumentCaptor.forClass(PurchaseEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue()).isEqualTo(PurchaseEvent.builder().sessionId(sessionId).productId(productId).quantity(5).build());
    }
}