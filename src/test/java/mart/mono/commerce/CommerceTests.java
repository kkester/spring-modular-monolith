package mart.mono.commerce;

import mart.mono.commerce.cart.CartService;
import mart.mono.commerce.purchase.Purchase;
import mart.mono.commerce.purchase.PurchaseCommandRepository;
import mart.mono.commerce.purchase.PurchaseQueryRepository;
import mart.mono.commerce.purchase.PurchasesJpaRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommerceTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CartService cartService;

    @Autowired
    PurchaseQueryRepository purchaseQueryRepository;

    private static JSONObject getV1ProductObject() throws JSONException {
        JSONObject jsonPayload = new JSONObject();

        jsonPayload.put("id", "d5d6d7d8-9c8b-4d3c-9f2e-1d5c5d2c5d2a")
                .put("catalogId", "electronics")
                .put("name", "Dell Laptop")
                .put("imageSrc", "https://picsum.photos/id/2/200/300")
                .put("imageAlt", "Products Alt Text")
                .put("description", "This is a description of Dell Laptop")
                .put("price", "10.99")
                .put("quantity", 10);
        return jsonPayload;
    }

    private static JSONObject getV2ProductObject() throws JSONException {
        JSONObject jsonPayload = new JSONObject();

        jsonPayload.put("id", "d5d6d7d8-9c8b-4d3c-9f2e-1d5c5d2c5d2a")
                .put("name", "Dell Laptop")
                .put("price", "10.99");
        return jsonPayload;
    }

    @BeforeEach
    void clearCart() {
        cartService.removeAll();
    }

    private ResultActions addV1ItemToCart() throws Exception {
        JSONObject jsonPayload = getV1ProductObject();

        MockHttpServletRequestBuilder request = post("/api/v1/cart")
                .contentType(APPLICATION_JSON)
                .content(jsonPayload.toString());

        return mockMvc.perform(request);
    }

    private ResultActions addV2ItemToCart() throws Exception {
        JSONObject jsonPayload = getV2ProductObject();

        MockHttpServletRequestBuilder request = post("/api/v1/cart")
                .contentType(APPLICATION_JSON)
                .content(jsonPayload.toString());

        return mockMvc.perform(request);
    }

    @Test
    void v1AddToCartContractTest() throws Exception {
        ResultActions resultActions = addV1ItemToCart();
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasKey("product")))
                .andExpect(jsonPath("$", hasKey("id")))
                .andExpect(jsonPath("$", hasKey("quantity")));
    }

    @Test
    void v2AddToCartContractTest() throws Exception {
        ResultActions resultActions = addV2ItemToCart();
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasKey("product")))
                .andExpect(jsonPath("$", hasKey("id")))
                .andExpect(jsonPath("$", hasKey("quantity")));
    }

    @Test
    void removeItemFromCartContractTest() throws Exception {
        ResultActions result = addV2ItemToCart();
        JSONObject resultJson = new JSONObject(result.andReturn().getResponse().getContentAsString());

        String uuid = resultJson.getString("id");

        mockMvc.perform(put("/api/v1/cart/{id}", uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", equalTo(0)));
    }

    @Test
    void getCartContractTest() throws Exception {
        addV2ItemToCart();

        mockMvc.perform(get("/api/v1/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$[0]", hasKey("product")))
                .andExpect(jsonPath("$[0]", hasKey("id")))
                .andExpect(jsonPath("$[0]", hasKey("quantity")))
                .andDo(print());
    }


    @Test
    void checkoutCartEndToEndTest() throws Exception {
        addV1ItemToCart()
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/cart/checkout"))
                .andExpect(status().isOk());

        List<Purchase> purchases = purchaseQueryRepository.findAll();
        assertThat(purchases).hasSize(1);
        Purchase purchase = purchases.get(0);
        assertThat(purchase.getItems()).hasSize(1);
    }
}
