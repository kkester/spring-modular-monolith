package mart.mono.commerce.cart;

public interface CartCommandRepository {
    CartItem save(CartItem cartItem);
    void delete(CartItem cartItem);
    void deleteAll();
}
