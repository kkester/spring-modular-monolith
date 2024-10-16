package mart.mono.commerce.purchase;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchases")
public class PurchaseEntity {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "purchase")
    @Builder.Default
    private List<PurchasedItemEntity> items = new ArrayList<>();

    public void applyItems(List<PurchasedItemEntity> purchasedItemEntities) {
        items.addAll(purchasedItemEntities);
        purchasedItemEntities.forEach(item -> item.setPurchase(this));
    }
}
