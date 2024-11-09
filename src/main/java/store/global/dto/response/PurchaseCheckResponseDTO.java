package store.global.dto.response;

import java.util.List;

public record PurchaseCheckResponseDTO(
        List<PromotionalProductInfo> promotionalPurchaseProductInfos,
        List<FullPriceProductInfo> fullPricePurchaseProductInfos) {
}
