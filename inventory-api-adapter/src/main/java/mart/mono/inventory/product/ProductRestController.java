package mart.mono.inventory.product;

import lombok.RequiredArgsConstructor;
import mart.mono.inventory.lib.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final GetProducts productRetriever;

    @GetMapping
    public List<Product> list(@RequestParam(value = "catalog", required = false) String catalogKey) {
        if (catalogKey == null) {
            return productRetriever.getAll();
        }
        return productRetriever.getForCatalog(catalogKey);
    }

    @GetMapping("/{id}")
    public Product list(@PathVariable UUID id) {
        return productRetriever.getProductById(id);
    }
}
