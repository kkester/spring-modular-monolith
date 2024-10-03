package mart.mono.inventory.product;

import mart.mono.inventory.lib.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Optional<Product> findById(UUID productId);
    List<Product> findByCatalogId(String catalogKey);
    List<Product> findAll();
    void updateQuantity(Product product);
}
