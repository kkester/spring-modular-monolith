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
public class CartService implements GetCarts, CartCommands {

    private final CartQueryRepository cartQueryRepository;
    private final CartCommandRepository cartCommandRepository;
    private final PurchasesService purchasesService;

    public List<CartItem> get() {
        return cartQueryRepository.findAll();
    }

    public CartItem add(Product product) {
        return cartCommandRepository.save(CartItem.builder()
            .product(Product.builder().id(product.getId()).build())
            .quantity(1)
            .build());
    }

    public void remove(UUID cartItemId) {
        Optional<CartItem> cartItem = cartQueryRepository.findById(cartItemId);
        cartItem.ifPresent(cartCommandRepository::delete);
    }

    public void removeAll() {
        cartCommandRepository.deleteAll();
    }

    public void checkOut() {
        List<CartItem> cart = cartQueryRepository.findAll();
        boolean purchaseSuccess = purchasesService.purchase(cart);
        if (purchaseSuccess) {
            cartCommandRepository.deleteAll();
        }
    }
}
