package mart.mono.commerce.purchase;

import mart.mono.commerce.cart.CartItem;
import mart.mono.inventory.lib.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PurchasesServiceTest {

    @Mock
    PurchaseQueryRepository purchaseQueryRepository;

    @Mock
    PurchaseCommandRepository purchaseCommandRepository;

    @Mock
    PurchaseEventPublisher purchaseEventPublisher;

    @InjectMocks
    PurchasesService purchasesService;

    @Test
    void purchase() {
        UUID productId = UUID.randomUUID();
        CartItem cartItem = CartItem.builder()
                .quantity(5)
                .product(Product.builder().id(productId).build())
                .build();

        boolean result = purchasesService.purchase(List.of(cartItem));

        assertThat(result).isTrue();
        ArgumentCaptor<Purchase> purchaseCaptor = ArgumentCaptor.forClass(Purchase.class);
        verify(purchaseCommandRepository).save(purchaseCaptor.capture());
        Purchase purchase = purchaseCaptor.getValue();
        assertThat(purchase.getItems()).hasSize(1);

        verify(purchaseEventPublisher).productPurchased(productId, 5);
    }
}