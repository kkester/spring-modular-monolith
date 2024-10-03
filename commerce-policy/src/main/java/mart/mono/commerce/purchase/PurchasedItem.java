package mart.mono.commerce.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchasedItem {
    private UUID id;
    private UUID productId;
    private Integer quantity;
}
