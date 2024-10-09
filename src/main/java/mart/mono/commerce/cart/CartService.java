package mart.mono.commerce.cart;

import lombok.RequiredArgsConstructor;
import mart.mono.commerce.cart.db.CartItemEntity;
import mart.mono.commerce.cart.db.CartRepository;
import mart.mono.commerce.purchase.PurchasesService;
import mart.mono.common.Product;
import mart.mono.inventory.ProductRetriever;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final PurchasesService purchasesService;
    private final ProductRetriever productRetriever;

    public List<CartItem> get() {
        return cartRepository.findAll().stream()
                .map(this::toCartItem)
                .toList();
    }

    private CartItem toCartItem(CartItemEntity cartItemEntity) {
        return CartItem.builder()
                .id(cartItemEntity.getId())
                .product(productRetriever.getProductById(cartItemEntity.getProductId()))
                .quantity(cartItemEntity.getQuantity())
                .build();
    }

    public CartItem add(Product product) {
        CartItemEntity cartItemEntity = CartItemEntity.builder()
                .productId(product.getId())
                .quantity(1)
                .build();
        return toCartItem(cartRepository.save(cartItemEntity));
    }

    public void remove(UUID cartItemId) {
        Optional<CartItemEntity> cartItem = cartRepository.findById(cartItemId);
        cartItem.ifPresent(cartRepository::delete);
    }

    public void checkOut() {
        List<CartItemEntity> cart = cartRepository.findAll();
        boolean purchaseSuccess = purchasesService.purchase(cart);
        if (purchaseSuccess) {
            cartRepository.deleteAll();
        }
    }
}
