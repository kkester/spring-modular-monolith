package mart.mono.inventory.catalog;

import lombok.RequiredArgsConstructor;
import mart.mono.common.Catalog;
import mart.mono.inventory.catalog.db.CatalogEntity;
import mart.mono.inventory.catalog.db.CatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public List<Catalog> getAll() {
        return catalogRepository.findAll().stream()
                .map(this::toCatalog)
                .toList();
    }

    public Catalog getCatalogById(String catalogId) {
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
