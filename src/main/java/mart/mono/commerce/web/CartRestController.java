package mart.mono.commerce.web;

import lombok.RequiredArgsConstructor;
import mart.mono.commerce.db.CartItemEntity;
import mart.mono.commerce.service.CartItem;
import mart.mono.commerce.service.CartService;
import mart.mono.common.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class CartRestController {

    private final CartService cartService;

    @GetMapping
    public List<CartItem> list() {
        return cartService.get();
    }

    @PostMapping
    public CartItem add(@RequestBody Product product) {
        return cartService.add(product);
    }

    @PutMapping("{id}")
    public List<CartItem> remove(@PathVariable UUID id) {
        cartService.remove(id);
        return cartService.get();
    }

    @PostMapping("checkout")
    public ResponseEntity<Void> checkOut() {
        cartService.checkOut();
        return ResponseEntity.ok().build();
    }
}
