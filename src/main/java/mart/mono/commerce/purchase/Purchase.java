package mart.mono.commerce.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
