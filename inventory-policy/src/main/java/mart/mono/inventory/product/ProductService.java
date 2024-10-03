package mart.mono.inventory.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mart.mono.inventory.lib.Product;
import mart.mono.inventory.lib.PurchaseEvent;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements PurchaseProductNotifier, GetProduct {

    private final ProductRepository productRepository;

    public Product getProductById(UUID productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
    }

    public List<Product> getForCatalog(String catalogKey) {
        return productRepository.findByCatalogId(catalogKey);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public void decrementProductQuantity(UUID productId, int quantity) {
        productRepository.findById(productId).ifPresent(product -> {
            int currentQuantity = product.getQuantity();
            int newQuantity = currentQuantity - quantity;
            if (newQuantity < 0) {
                return;
            }
            product.setQuantity(newQuantity);
            productRepository.updateQuantity(product);
        });
    }

    public void processPurchaseEvent(PurchaseEvent purchaseEvent) {
        log.info("Processing Event {}", purchaseEvent);
        decrementProductQuantity(purchaseEvent.getProductId(), purchaseEvent.getQuantity());
    }
}
