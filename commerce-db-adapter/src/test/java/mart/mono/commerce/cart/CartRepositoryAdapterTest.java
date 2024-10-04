package mart.mono.commerce.cart;

import mart.mono.inventory.lib.Catalog;
import mart.mono.inventory.lib.Product;
import mart.mono.inventory.product.GetProducts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartRepositoryAdapterTest {

    @Mock
    CartJpaRepository cartJpaRepository;

    @Mock
    GetProducts getProducts;

    @InjectMocks
    CartRepositoryAdapter adapter;

    @Test
    void save() {
        UUID productId = UUID.randomUUID();
        CartItem cartItem = CartItem.builder()
                .product(Product.builder().id(productId).build())
                .quantity(5)
                .build();
        CartItemEntity cartItemEntity = CartItemEntity.builder()
                .id(UUID.randomUUID())
                .productId(productId)
                .quantity(5)
                .build();
        ArgumentCaptor<CartItemEntity> captor = ArgumentCaptor.forClass(CartItemEntity.class);
        when(cartJpaRepository.save(captor.capture())).thenReturn(cartItemEntity);

        Product product = Product.builder()
                .id(productId)
                .quantity(10)
                .name("My Product")
                .description("My Description")
                .price("$1,000,000")
                .catalog(Catalog.builder().displayName("My Catalog").build())
                .build();
        when(getProducts.getProductById(cartItemEntity.getProductId())).thenReturn(product);

        CartItem savedCartItem = adapter.save(cartItem);

        CartItem expectedCartItem = CartItem.builder()
                .id(cartItemEntity.getId())
                .quantity(cartItemEntity.getQuantity())
                .product(product)
                .build();
        assertThat(savedCartItem).isEqualTo(expectedCartItem);
    }
}