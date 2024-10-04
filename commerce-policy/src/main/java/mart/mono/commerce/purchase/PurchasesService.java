package mart.mono.commerce.purchase;

import lombok.RequiredArgsConstructor;
import mart.mono.commerce.cart.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchasesService implements GetPurchases {

    private final PurchaseQueryRepository purchaseQueryRepository;
    private final PurchaseCommandRepository purchaseCommandRepository;
    private final PurchaseEventPublisher purchaseEventPublisher;

    public List<Purchase> getAll() {
        return purchaseQueryRepository.findAll();
    }

    public boolean purchase(List<CartItem> cartItems) {
        try {
            List<PurchasedItem> purchasedItems = cartItems.stream().map(this::toItem).toList();
            purchaseCommandRepository.save(Purchase.builder().items(purchasedItems).build());
            cartItems.forEach(cartItem -> {
                purchaseEventPublisher.productPurchased(cartItem.getProduct().getId(), cartItem.getQuantity());
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private PurchasedItem toItem(CartItem cartItem) {
        return PurchasedItem.builder()
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
