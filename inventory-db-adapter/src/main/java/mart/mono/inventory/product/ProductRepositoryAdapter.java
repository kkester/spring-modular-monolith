package mart.mono.inventory.product;

import lombok.RequiredArgsConstructor;
import mart.mono.inventory.catalog.CatalogRepository;
import mart.mono.inventory.lib.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository productRepository;
    private final CatalogRepository catalogRepository;

    @Override
    public Optional<Product> findById(UUID productId) {
        return productRepository.findById(productId)
                .map(this::toProduct);
    }

    @Override
    public List<Product> findByCatalogId(String catalogKey) {
        return productRepository.findByCatalogId(catalogKey).stream()
                .map(this::toProduct)
                .toList();
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll().stream()
                .map(this::toProduct)
                .toList();
    }

    @Override
    public void updateQuantity(Product product) {
        productRepository.findById(product.getId())
                .map(productEntity -> update(product, productEntity))
                .ifPresent(productRepository::save);
    }

    private ProductEntity update(Product product, ProductEntity productEntity) {
        productEntity.setQuantity(product.getQuantity());
        return productEntity;
    }

    private Product toProduct(ProductEntity productEntity) {
        return Product.builder()
                .id(productEntity.getId())
                .catalog(catalogRepository.findById(productEntity.getCatalog().getId()))
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .imageSrc(productEntity.getImageSrc())
                .imageAlt(productEntity.getImageAlt())
                .quantity(productEntity.getQuantity())
                .price(productEntity.getPrice())
                .build();
    }

    private ProductEntity toEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .imageSrc(product.getImageSrc())
                .imageAlt(product.getImageAlt())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
    }
}
