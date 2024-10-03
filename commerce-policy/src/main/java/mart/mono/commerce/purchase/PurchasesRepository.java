package mart.mono.commerce.purchase;

import java.util.List;

public interface PurchasesRepository {
    List<Purchase> findAll();
    Purchase save(Purchase purchase);
}
