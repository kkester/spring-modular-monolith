package mart.mono.commerce.service;

import lombok.RequiredArgsConstructor;
import mart.mono.commerce.db.CartItemEntity;
import mart.mono.commerce.db.PurchaseEntity;
import mart.mono.commerce.db.PurchaseRepository;
import mart.mono.commerce.db.PurchasedItemEntity;
import mart.mono.common.PurchaseEvent;
import mart.mono.inventory.ProductRetriever;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchasesService {

    private final ProductRetriever productRetriever;
    private final PurchaseRepository purchaseRepository;
    private final ApplicationEventPublisher eventPublisher;

    public List<Purchase> getAll() {
        return purchaseRepository.findAll().stream()
                .map(this::toPurchase)
                .toList();
    }

    private Purchase toPurchase(PurchaseEntity purchaseEntity) {
        return Purchase.builder()
                .id(purchaseEntity.getId())
                .items(purchaseEntity.getItems().stream()
                        .map(this::toPurchaseItem)
                        .toList())
                .build();
    }

    private PurchasedItem toPurchaseItem(PurchasedItemEntity purchasedItemEntity) {
        return PurchasedItem.builder()
                .id(purchasedItemEntity.getId())
                .product(productRetriever.getProductById(purchasedItemEntity.getProductId()))
                .quantity(purchasedItemEntity.getQuantity())
                .build();
    }

    @Transactional
    public boolean purchase(List<CartItemEntity> cartItems) {
        try {
            List<PurchasedItemEntity> items = cartItems.stream()
                    .map(cartItem -> PurchasedItemEntity.builder()
                            .productId(cartItem.getProductId())
                            .quantity(cartItem.getQuantity())
                            .build())
                    .toList();

            PurchaseEntity purchase = new PurchaseEntity();
            purchase.applyItems(items);
            purchaseRepository.save(purchase);

            cartItems.forEach(cartItem ->
                    eventPublisher.publishEvent(PurchaseEvent.builder()
                            .productId(cartItem.getProductId())
                            .quantity(cartItem.getQuantity())
                            .build())
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
