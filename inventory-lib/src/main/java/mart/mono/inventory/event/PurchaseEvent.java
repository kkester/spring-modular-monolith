package mart.mono.inventory.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseEvent {
    private String sessionId;
    private UUID productId;
    private Integer quantity;
}
