package mart.mono.inventory.product;

import mart.mono.inventory.event.PurchaseEvent;

public interface PurchaseProductNotifier {
    void processPurchaseEvent(PurchaseEvent purchaseEvent);
}
