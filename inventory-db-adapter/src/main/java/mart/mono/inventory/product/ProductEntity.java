package mart.mono.inventory.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mart.mono.inventory.catalog.CatalogEntity;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class ProductEntity {
    @Id
    private UUID id;

    @OneToOne
    private CatalogEntity catalog;

    private String name;
    private String price;
    private String description;
    private String imageSrc;
    private String imageAlt;

    private int quantity;
}
