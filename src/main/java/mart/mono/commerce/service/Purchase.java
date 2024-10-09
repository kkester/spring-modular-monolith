package mart.mono.commerce.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mart.mono.commerce.db.PurchasedItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    private UUID id;
    @Builder.Default
    private List<PurchasedItem> items = new ArrayList<>();
}
