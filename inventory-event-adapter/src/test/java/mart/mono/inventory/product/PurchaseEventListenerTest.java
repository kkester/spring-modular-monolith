package mart.mono.inventory.product;

import mart.mono.inventory.lib.PurchaseEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PurchaseEventListenerTest {

    @InjectMocks
    PurchaseEventListener purchaseEventListener;

    @Mock
    PurchaseProductNotifier purchaseProductNotifier;

    @Test
    void onPurchaseTest() {
        PurchaseEvent purchaseEvent = PurchaseEvent.builder().build();

        purchaseEventListener.onPurchase(purchaseEvent);

        verify(purchaseProductNotifier).processPurchaseEvent(purchaseEvent);
    }
}