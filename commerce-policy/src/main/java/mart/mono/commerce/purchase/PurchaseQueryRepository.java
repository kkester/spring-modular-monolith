package mart.mono.commerce.purchase;

import java.util.List;

public interface PurchaseQueryRepository {
    List<Purchase> findAll();
}
