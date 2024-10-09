package mart.mono.purchase;

import mart.mono.inventory.db.ProductEntity;
import mart.mono.commerce.db.PurchaseEntity;
import mart.mono.commerce.db.PurchasedItemEntity;
import mart.mono.inventory.db.ProductRepository;
import mart.mono.commerce.db.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PurchaseTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void getPurchasesTest() throws Exception {

        ProductEntity productEntity = productRepository.findAll().get(0);
        PurchasedItemEntity purchasedItemEntity = PurchasedItemEntity.builder()
                .productId(productEntity.getId())
                .quantity(3)
                .build();
        PurchaseEntity purchase = new PurchaseEntity();
        purchase.applyItems(List.of(purchasedItemEntity));
        purchaseRepository.save(purchase);

        mockMvc.perform(get("/api/purchases"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0]", hasKey("id")))
            .andExpect(jsonPath("$[0]", hasKey("items")))
            .andExpect(jsonPath("$[0].items[0]", hasKey("id")))
            .andExpect(jsonPath("$[0].items[0]", hasKey("product")))
            .andExpect(jsonPath("$[0].items[0]", hasKey("quantity")))
            .andDo(print());
    }

}
