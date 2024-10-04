package mart.mono.commerce.purchase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PurchasesRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PurchaseQueryRepository purchaseQueryRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getPurchasesTest() throws Exception {
        Purchase expectedPurchase = Purchase.builder()
                .id(UUID.randomUUID())
                .items(List.of(PurchasedItem.builder().build()))
                .build();
        when(purchaseQueryRepository.findAll()).thenReturn(List.of(expectedPurchase));

        MockHttpServletRequestBuilder request = get("/api/purchases");
        String responseContent = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Purchase> actualPurchases = objectMapper.readValue(responseContent, new TypeReference<>() {
        });
        assertThat(actualPurchases).containsExactly(expectedPurchase);
    }
}