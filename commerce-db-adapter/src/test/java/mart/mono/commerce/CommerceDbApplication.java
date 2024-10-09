package mart.mono.commerce;

import mart.mono.commerce.cart.CartEventPublisher;
import mart.mono.commerce.cart.CartItem;
import mart.mono.commerce.purchase.PurchaseEventPublisher;
import mart.mono.inventory.lib.Product;
import mart.mono.inventory.product.GetProducts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class CommerceDbApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommerceDbApplication.class, args);
    }

    @Bean
    GetProducts getProducts() {
        return new GetProducts() {
            @Override
            public Product getProductById(UUID productId) {
                return null;
            }

            @Override
            public List<Product> getAll() {
                return Collections.emptyList();
            }

            @Override
            public List<Product> getForCatalog(String catalogKey) {
                return Collections.emptyList();
            }
        };
    };

    @Bean
    PurchaseEventPublisher purchaseEventPublisher() {
        return (productId, quantity) -> {
        };
    };

    @Bean
    CartEventPublisher cartEventPublisher() {
        return new CartEventPublisher() {
            @Override
            public void productAddedToCart(UUID productId, Integer quantity) {}

            @Override
            public void productRemovedFromCart(CartItem cartItem) {}
        };
    }
}
