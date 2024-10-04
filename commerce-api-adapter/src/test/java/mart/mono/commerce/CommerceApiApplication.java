package mart.mono.commerce;

import mart.mono.commerce.cart.CartCommandRepository;
import mart.mono.commerce.cart.CartItem;
import mart.mono.commerce.cart.CartQueryRepository;
import mart.mono.commerce.purchase.PurchaseCommandRepository;
import mart.mono.commerce.purchase.PurchaseEventPublisher;
import mart.mono.commerce.purchase.PurchaseQueryRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class CommerceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommerceApiApplication.class, args);
    }

    @Bean
    PurchaseQueryRepository purchaseQueryRepository() {
        return Collections::emptyList;
    }

    @Bean
    PurchaseCommandRepository purchaseCommandRepository() {
        return purchase -> purchase;
    }

    @Bean
    CartQueryRepository cartQueryRepository() {
        return new CartQueryRepository() {
            @Override
            public List<CartItem> findAll() {
                return Collections.emptyList();
            }

            @Override
            public Optional<CartItem> findById(UUID cartItemId) {
                return Optional.empty();
            }
        };
    }

    @Bean
    CartCommandRepository cartCommandRepository() {
        return new CartCommandRepository() {
            @Override
            public CartItem save(CartItem cartItem) {
                return null;
            }

            @Override
            public void delete(CartItem cartItem) {

            }

            @Override
            public void deleteAll() {

            }
        };
    }

    @Bean
    PurchaseEventPublisher purchaseEventPublisher() {
        return (productId, quantity) -> {};
    }
}
