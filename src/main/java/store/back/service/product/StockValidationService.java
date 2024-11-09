package store.back.service.product;

import java.util.List;
import store.back.domain.Product;
import store.global.dto.request.purchase.PurchaseProductInfo;
import store.global.exception.InvalidProductNameException;
import store.global.exception.OutOfStockException;

public class StockValidationService {
    private final ProductQueryService productQueryService = new ProductQueryService();

    public void validate(List<PurchaseProductInfo> purchaseProductInfos) {
        purchaseProductInfos.forEach(this::validateProductName);
        purchaseProductInfos.forEach(this::validatePurchasable);
    }

    private void validateProductName(PurchaseProductInfo purchaseProductInfo) {
        List<Product> products = productQueryService.findByName(purchaseProductInfo.name());

        if (products.isEmpty()) {
            throw new InvalidProductNameException();
        }
    }

    private void validatePurchasable(PurchaseProductInfo PurchaseProductInfo) {
        List<Product> products = productQueryService.findByName(PurchaseProductInfo.name());

        int totalQuantity = products.stream().mapToInt(Product::getQuantity).sum();
        if (totalQuantity < PurchaseProductInfo.quantity()) {
            throw new OutOfStockException();
        }
    }
}
