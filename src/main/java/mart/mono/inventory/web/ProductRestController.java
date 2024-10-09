package mart.mono.inventory.web;

import lombok.RequiredArgsConstructor;
import mart.mono.common.Product;
import mart.mono.inventory.db.ProductEntity;
import mart.mono.inventory.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @GetMapping
    public List<Product> list(@RequestParam(value = "catalog", required = false) String catalogKey) {
        if (catalogKey == null) {
            return productService.getAll();
        }

        return productService.getForCatalog(catalogKey);
    }
}
