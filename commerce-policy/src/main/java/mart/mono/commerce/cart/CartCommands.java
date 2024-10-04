package mart.mono.commerce.cart;

import mart.mono.inventory.lib.Product;

import java.util.UUID;

public interface CartCommands {
    CartItem add(Product product);
    void remove(UUID cartItemId);
    void checkOut();
}
