package mart.mono.purchase;

import mart.mono.commerce.purchase.Purchase;
import mart.mono.commerce.purchase.PurchaseCommandRepository;
import mart.mono.commerce.purchase.PurchasedItem;
import mart.mono.inventory.lib.Product;
import mart.mono.inventory.product.ProductQueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PurchaseCommandRepository purchaseCommandRepository;

    @Autowired
    ProductQueryRepository productQueryRepository;

    @Test
    void getPurchasesTest() throws Exception {

        Product product = productQueryRepository.findAll().get(0);
        PurchasedItem purchasedItem = PurchasedItem.builder()
                .productId(product.getId())
                .quantity(3)
                .build();
        Purchase purchase = Purchase.builder().items(List.of(purchasedItem)).build();
        purchaseCommandRepository.save(purchase);

        mockMvc.perform(get("/api/purchases"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0]", hasKey("id")))
            .andExpect(jsonPath("$[0]", hasKey("items")))
            .andExpect(jsonPath("$[0].items[0]", hasKey("id")))
            .andExpect(jsonPath("$[0].items[0]", hasKey("productId")))
            .andExpect(jsonPath("$[0].items[0]", hasKey("quantity")))
            .andDo(print());
    }

}
