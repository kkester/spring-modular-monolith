package mart.mono.inventory.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mart.mono.inventory.lib.Product;
import mart.mono.inventory.lib.PurchaseEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseEventListener {

    private final PurchaseProductNotifier purchaseProductNotifier;

    @Async
    @EventListener
    public void onPurchase(PurchaseEvent event) {
        purchaseProductNotifier.processPurchaseEvent(event);
    }
}
