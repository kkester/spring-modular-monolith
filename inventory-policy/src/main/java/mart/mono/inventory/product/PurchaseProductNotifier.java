package mart.mono.inventory.product;

import mart.mono.inventory.lib.PurchaseEvent;

public interface PurchaseProductNotifier {
    void processPurchaseEvent(PurchaseEvent purchaseEvent);
}
