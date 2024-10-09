package mart.mono.inventory;

import mart.mono.common.Product;

import java.util.UUID;

public interface ProductRetriever {
    Product getProductById(UUID productId);
}
