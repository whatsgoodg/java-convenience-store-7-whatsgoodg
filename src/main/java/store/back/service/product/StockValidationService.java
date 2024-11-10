package store.back.service.product;

import java.util.List;
import java.util.Optional;
import store.back.domain.order.OrderLine;
import store.back.domain.order.OrderLineStatus;
import store.back.domain.product.Product;
import store.global.dto.request.purchase.PurchaseProductInfo;
import store.global.exception.InvalidProductNameException;
import store.global.exception.OutOfStockException;

public class StockValidationService {
    private final ProductQueryService productQueryService = new ProductQueryService();

    public void validatePurchasable(List<PurchaseProductInfo> purchaseProductInfos) {
        purchaseProductInfos.forEach(this::validateProductName);
        purchaseProductInfos.forEach(this::validateStocks);
    }

    private void validateProductName(PurchaseProductInfo purchaseProductInfo) {
        List<Product> products = productQueryService.findByName(purchaseProductInfo.name());

        if (products.isEmpty()) {
            throw new InvalidProductNameException();
        }
    }

    private void validateStocks(PurchaseProductInfo PurchaseProductInfo) {
        List<Product> products = productQueryService.findByName(PurchaseProductInfo.name());

        int totalQuantity = products.stream().mapToInt(Product::getQuantity).sum();
        if (totalQuantity < PurchaseProductInfo.quantity()) {
            throw new OutOfStockException();
        }
    }

//    public void validateOrderLines(OrderLine orderLine) {
//        Optional<Product> productWithPromotion = productQueryService.findProductWithPromotion(
//                orderLine.getProductName());
//        Optional<Product> productNoPromotion = productQueryService.findProductNoPromotion(
//                orderLine.getProductName());
//        Integer promotionQuantity = productWithPromotion.map(Product::getQuantity).orElse(0);
//        Integer noPromotionQuantity = productNoPromotion.map(Product::getQuantity).orElse(0);
//    }
}
