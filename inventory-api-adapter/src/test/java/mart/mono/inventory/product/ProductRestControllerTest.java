package mart.mono.inventory.product;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductQueryRepository productQueryRepository;

    @Test
    void getProductsTest() throws Exception {
        Product expectedProduct = Product.builder()
                .id(UUID.randomUUID())
                .build();
        when(productQueryRepository.findAll()).thenReturn(List.of(expectedProduct));

        MockHttpServletRequestBuilder request = get("/api/products");
        String responseContent = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Product> actualProducts = objectMapper.readValue(responseContent, new TypeReference<>() {
        });
        assertThat(actualProducts).containsExactly(expectedProduct);
    }
}