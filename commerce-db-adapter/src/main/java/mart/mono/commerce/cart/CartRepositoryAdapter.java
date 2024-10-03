package mart.mono.commerce.cart;

import lombok.RequiredArgsConstructor;
import mart.mono.inventory.product.GetProduct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartRepositoryAdapter implements CartRepository {

    private final CartJpaRepository cartJpaRepository;
    private final GetProduct productRetriever;

    @Override
    public List<CartItem> findAll() {
        return cartJpaRepository.findAll().stream()
                .map(this::toCartItem)
                .toList();
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return toCartItem(cartJpaRepository.save(toCartItemEntity(cartItem)));
    }

    @Override
    public Optional<CartItem> findById(UUID cartItemId) {
        return cartJpaRepository.findById(cartItemId)
                .map(this::toCartItem);
    }

    @Override
    public void delete(CartItem cartItem) {
        cartJpaRepository.deleteById(cartItem.getId());
    }

    @Override
    public void deleteAll() {
        cartJpaRepository.deleteAll();
    }

    private CartItemEntity toCartItemEntity(CartItem cartItem) {
        return CartItemEntity.builder()
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .build();
    }

    private CartItem toCartItem(CartItemEntity cartItemEntity) {
        return CartItem.builder()
                .id(cartItemEntity.getId())
                .quantity(cartItemEntity.getQuantity())
                .product(productRetriever.getProductById(cartItemEntity.getProductId()))
                .build();
    }
}
