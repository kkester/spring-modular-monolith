package mart.mono.inventory.catalog;

import lombok.RequiredArgsConstructor;
import mart.mono.inventory.lib.Catalog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService implements GetCatalogs {

    private final CatalogRepository catalogRepository;

    public List<Catalog> getAll() {
        return catalogRepository.findAll();
    }
}
