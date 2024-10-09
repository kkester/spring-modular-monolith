package mart.mono.inventory.catalog;

import lombok.RequiredArgsConstructor;
import mart.mono.common.Catalog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/catalogs")
@RequiredArgsConstructor
public class CatalogRestController {

    private final CatalogService catalogService;

    @GetMapping
    public List<Catalog> list() {
        return catalogService.getAll();
    }

}
