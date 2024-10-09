package mart.mono.inventory.service;

import io.micrometer.tracing.annotation.NewSpan;
import lombok.RequiredArgsConstructor;
import mart.mono.common.Product;
import mart.mono.common.PurchaseEvent;
import mart.mono.inventory.ProductRetriever;
import mart.mono.inventory.db.ProductEntity;
import mart.mono.inventory.db.ProductRepository;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductRetriever {

    private final ProductRepository productRepository;

    private final CatalogService catalogService;

    @ApplicationModuleListener
    public void onPurchaseEvent(PurchaseEvent event) {
        decrementProductQuantity(event.getProductId(), event.getQuantity());
    }

    private void decrementProductQuantity(UUID productId, int quantity) {
        Optional<ProductEntity> product = productRepository.findById(productId);
        if(product.isPresent()) {
            ProductEntity updatedProductEntity = product.get();
            int currentQuantity = product.get().getQuantity();
            int newQuantity = currentQuantity - quantity;

            if (newQuantity < 0) {
                return;
            }

            updatedProductEntity.setQuantity(newQuantity);
            productRepository.save(updatedProductEntity);
        }
    }

    public List<Product> getForCatalog(String catalogKey) {
        return productRepository.findByCatalogId(catalogKey).stream()
                .map(this::toProduct)
                .toList();
    }

    public List<Product> getAll() {
        return productRepository.findAll().stream()
                .map(this::toProduct)
                .toList();
    }

    private Product toProduct(ProductEntity productEntity) {
        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .imageAlt(productEntity.getImageAlt())
                .imageSrc(productEntity.getImageSrc())
                .quantity(productEntity.getQuantity())
                .catalog(catalogService.getCatalogById(productEntity.getCatalog().getId()))
                .build();
    }

    @NewSpan
    @Override
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .map(this::toProduct)
                .orElseThrow();
    }
}
