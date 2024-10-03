package mart.mono.inventory.product;

import mart.mono.inventory.lib.Product;

import java.util.UUID;

public interface GetProduct {
    Product getProductById(UUID productId);
}
