package store.back.facade.product;

import java.util.List;
import store.back.domain.Product;
import store.back.service.product.ProductQueryService;
import store.global.dto.response.display.ProductDisplayResponseDTO;
import store.global.dto.response.display.ProductInfo;

public class ProductDisplayFacade {
    private final ProductQueryService productQueryService = new ProductQueryService();

    public ProductDisplayResponseDTO getAllProducts() {
        List<Product> products = productQueryService.findAll();
        List<ProductInfo> productInfos = products.stream().map(ProductInfo::from).toList();

        return new ProductDisplayResponseDTO(productInfos);
    }
}
