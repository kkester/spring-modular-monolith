package mart.mono.commerce.purchase;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/purchases")
@RequiredArgsConstructor
public class PurchasesRestController {

    private final GetPurchases purchasesRetriever;

    @GetMapping
    public List<Purchase> list() {
        return purchasesRetriever.getAll();
    }
}
