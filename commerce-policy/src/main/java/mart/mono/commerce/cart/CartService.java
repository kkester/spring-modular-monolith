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
    private final CartEventPublisher cartEventPublisher;

    public List<CartItem> get() {
        return cartQueryRepository.findAll();
    }

    public CartItem add(Product product) {
        CartItem savedCartItem = cartCommandRepository.save(CartItem.builder()
                .product(Product.builder().id(product.getId()).build())
                .quantity(1)
                .build());
        cartEventPublisher.productAddedToCart(savedCartItem.getProduct().getId(), savedCartItem.getQuantity());
        return savedCartItem;
    }

    public void remove(UUID cartItemId) {
        Optional<CartItem> cartItemOptional = cartQueryRepository.findById(cartItemId);
        cartItemOptional.ifPresent(cartItem -> {
            cartCommandRepository.delete(cartItem);
            cartEventPublisher.productRemovedFromCart(cartItem);
        });
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
