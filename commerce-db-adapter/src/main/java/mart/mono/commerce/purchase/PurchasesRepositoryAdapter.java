package mart.mono.commerce.purchase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PurchasesRepositoryAdapter implements PurchasesRepository {

    private final PurchasesJpaRepository purchasesJpaRepository;

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
                .items(purchaseEntity.getItems().stream().map(this::toItem).toList())
                .build();
    }

    private PurchasedItem toItem(PurchasedItemEntity purchasedItemEntity) {
        return PurchasedItem.builder()
                .build();
    }

    private PurchaseEntity toEntity(Purchase purchase) {
        return PurchaseEntity.builder()
                .id(purchase.getId())
                .items(purchase.getItems().stream().map(this::toItemEntity).toList())
                .build();
    }

    private PurchasedItemEntity toItemEntity(PurchasedItem purchasedItem) {
        return PurchasedItemEntity.builder()
                .build();
    }
}
