package mart.mono.commerce.purchase;

import lombok.RequiredArgsConstructor;
import mart.mono.commerce.cart.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class PurchasesService {

    private final PurchasesRepository purchasesRepository;

    private final PurchaseEventPublisher purchaseEventPublisher;

    public List<Purchase> getAll() {
        return purchasesRepository.findAll();
    }

    public boolean purchase(List<CartItem> cartItems) {
        try {
            purchasesRepository.save(new Purchase(UUID.randomUUID(), emptyList()));
            cartItems.forEach(cartItem -> {
                purchaseEventPublisher.productPurchased(cartItem.getProduct().getId(), cartItem.getQuantity());
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
