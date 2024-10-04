package mart.mono.commerce.cart;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mart.mono.inventory.lib.Catalog;
import mart.mono.inventory.lib.Product;
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
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CartCommands cartCommands;

    @MockBean
    GetCarts cartRetriever;

    @Autowired
    ObjectMapper objectMapper;

    private static Product getProductObject() {
        return Product.builder()
                .id(UUID.fromString("d5d6d7d8-9c8b-4d3c-9f2e-1d5c5d2c5d2a"))
                .catalog(Catalog.builder().displayName("electronics").build())
                .name("Dell Laptop")
                .imageSrc("https://picsum.photos/id/2/200/300")
                .imageAlt("Products Alt Text")
                .description("This is a description of Dell Laptop")
                .price("10.99")
                .quantity(10)
                .build();
    }

    private static CartItem getCartItem(Product product) {
        return CartItem.builder()
                .id(UUID.randomUUID())
                .product(product)
                .quantity(1)
                .build();
    }

    @Test
    void addToCartTest() throws Exception {
        Product product = getProductObject();
        CartItem expectedCartItem = getCartItem(product);
        when(cartCommands.add(product)).thenReturn(expectedCartItem);

        MockHttpServletRequestBuilder request = post("/api/v1/cart")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product));
        String responseContent = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CartItem actualCartItem = objectMapper.readValue(responseContent, CartItem.class);
        assertThat(actualCartItem).isEqualTo(expectedCartItem);
    }

    @Test
    void getCartTest() throws Exception {
        Product product = getProductObject();
        CartItem expectedCartItem = getCartItem(product);
        when(cartRetriever.get()).thenReturn(List.of(expectedCartItem));

        MockHttpServletRequestBuilder request = get("/api/v1/cart");
        String responseContent = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<CartItem> actualCartItem = objectMapper.readValue(responseContent, new TypeReference<>() {
        });
        assertThat(actualCartItem).containsExactly(expectedCartItem);
    }
}