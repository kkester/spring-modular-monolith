package mart.mono.commerce.cart;

import lombok.RequiredArgsConstructor;
import mart.mono.commerce.purchase.PurchasesService;
import mart.mono.inventory.lib.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final PurchasesService purchasesService;

    public List<CartItem> get() {
        return cartRepository.findAll();
    }

    public CartItem add(Product product) {
        return cartRepository.save(CartItem.builder()
            .product(Product.builder().id(product.getId()).build())
            .quantity(1)
            .build());
    }

    public void remove(UUID cartItemId) {
        Optional<CartItem> cartItem = cartRepository.findById(cartItemId);
        cartItem.ifPresent(cartRepository::delete);
    }

    public void removeAll() {
        cartRepository.deleteAll();
    }

    public void checkOut() {
        List<CartItem> cart = cartRepository.findAll();
        boolean purchaseSuccess = purchasesService.purchase(cart);
        if (purchaseSuccess) {
            cartRepository.deleteAll();
        }
    }
}
