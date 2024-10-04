package mart.mono.commerce.purchase;

import mart.mono.inventory.lib.PurchaseEvent;
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

@ExtendWith(SpringExtension.class)
class PurchaseEventPublisherAdapterTest {

    @InjectMocks
     PurchaseEventPublisherAdapter purchaseEventPublisher;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Test
    void productPurchasedTest() {
        UUID productId = UUID.randomUUID();
        purchaseEventPublisher.productPurchased(productId, 5);

        ArgumentCaptor<PurchaseEvent> captor = ArgumentCaptor.forClass(PurchaseEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue()).isEqualTo(PurchaseEvent.builder().productId(productId).quantity(5).build());
    }
}