package mart.mono.inventory.catalog;

import lombok.RequiredArgsConstructor;
import mart.mono.inventory.lib.Catalog;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CatalogRepositoryAdapter implements CatalogQueryRepository {

    private final CatalogJpaRepository catalogRepository;

    @Override
    public List<Catalog> findAll() {
        return catalogRepository.findAll().stream()
                .map(this::toCatalog)
                .toList();
    }

    @Override
    public Catalog findById(String catalogId) {
        return catalogRepository.findById(catalogId)
                .map(this::toCatalog)
                .orElseThrow();
    }

    private Catalog toCatalog(CatalogEntity catalogEntity) {
        return Catalog.builder()
                .id(catalogEntity.getId())
                .displayName(catalogEntity.getDisplayName())
                .build();
    }
}
