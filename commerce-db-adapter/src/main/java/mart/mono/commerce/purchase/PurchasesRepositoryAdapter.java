package mart.mono.commerce.purchase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PurchasesRepositoryAdapter implements PurchaseQueryRepository, PurchaseCommandRepository {

    private final PurchasesJpaRepository purchasesJpaRepository;

    @Transactional
    @Override
    public List<Purchase> findAll() {
        return purchasesJpaRepository.findAll().stream()
                .map(this::toPurchase)
                .toList();
    }

    @Override
    public Purchase save(Purchase purchase) {
        return toPurchase(purchasesJpaRepository.save(toEntity(purchase)));
    }

    private Purchase toPurchase(PurchaseEntity purchaseEntity) {
        return Purchase.builder()
                .id(purchaseEntity.getId())
                .items(purchaseEntity.getItems().stream().map(this::toPurchaseItem).toList())
                .build();
    }

    private PurchasedItem toPurchaseItem(PurchasedItemEntity purchasedItemEntity) {
        return PurchasedItem.builder()
                .id(purchasedItemEntity.getId())
                .productId(purchasedItemEntity.getProductId())
                .quantity(purchasedItemEntity.getQuantity())
                .build();
    }

    private PurchaseEntity toEntity(Purchase purchase) {
        List<PurchasedItemEntity> purchasedItemEntities = purchase.getItems().stream().map(this::toItemEntity).toList();
        PurchaseEntity purchaseEntity = PurchaseEntity.builder()
                .id(purchase.getId())
                .build();
        purchaseEntity.applyItems(purchasedItemEntities);
        return purchaseEntity;
    }

    private PurchasedItemEntity toItemEntity(PurchasedItem purchasedItem) {
        return PurchasedItemEntity.builder()
                .productId(purchasedItem.getProductId())
                .quantity(purchasedItem.getQuantity())
                .build();
    }
}
