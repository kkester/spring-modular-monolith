package mart.mono.inventory.web;

import lombok.RequiredArgsConstructor;
import mart.mono.common.Catalog;
import mart.mono.inventory.service.CatalogService;
import mart.mono.inventory.db.CatalogEntity;
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
