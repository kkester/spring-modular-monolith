package mart.mono.inventory.catalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CatalogJpaRepository extends JpaRepository<CatalogEntity, String> {
}
