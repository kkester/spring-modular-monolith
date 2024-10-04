package mart.mono.inventory.product;

import mart.mono.inventory.lib.Product;

import java.util.List;
import java.util.UUID;

public interface GetProducts {
    Product getProductById(UUID productId);
    List<Product> getAll();
    List<Product> getForCatalog(String catalogKey);
}
