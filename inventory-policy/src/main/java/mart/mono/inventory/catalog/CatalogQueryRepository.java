package mart.mono.inventory.catalog;

import mart.mono.inventory.lib.Catalog;

import java.util.List;

public interface CatalogQueryRepository {
    List<Catalog> findAll();
    Catalog findById(String catalogId);
}
