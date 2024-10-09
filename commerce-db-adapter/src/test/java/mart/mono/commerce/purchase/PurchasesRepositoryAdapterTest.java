package mart.mono.commerce.purchase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PurchasesRepositoryAdapterTest {

    @Autowired
    PurchasesRepositoryAdapter purchasesRepositoryAdapter;

    @Test
    void saveTest() {
        PurchasedItem purchasedItem = PurchasedItem.builder()
                .productId(UUID.randomUUID())
                .quantity(5)
                .build();
        Purchase purchase = Purchase.builder()
                .items(List.of(purchasedItem))
                .build();

        Purchase savedPurchase = purchasesRepositoryAdapter.save(purchase);

        assertThat(savedPurchase.getItems()).hasSize(1);
        assertThat(savedPurchase.getItems().get(0).getProductId()).isEqualTo(purchasedItem.getProductId());
        assertThat(savedPurchase.getItems().get(0).getQuantity()).isEqualTo(purchasedItem.getQuantity());
    }
}