package mart.mono.commerce.cart;

import lombok.RequiredArgsConstructor;
import mart.mono.inventory.lib.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CartRestController {

    private final GetCarts cartRetriever;
    private final CartCommands cartCommands;

    @GetMapping("api/v1/cart")
    public List<CartItem> list() {
        return cartRetriever.get();
    }

    @PostMapping("api/v1/cart")
    public CartItem add(@RequestBody Product product) {
        return cartCommands.add(product);
    }

    @PutMapping("api/v1/cart/{id}")
    public List<CartItem> remove(@PathVariable UUID id) {
        cartCommands.remove(id);
        return cartRetriever.get();
    }

    @PostMapping("api/v1/cart/checkout")
    public ResponseEntity<Void> checkOut() {
        cartCommands.checkOut();
        return ResponseEntity.ok().build();
    }
}
