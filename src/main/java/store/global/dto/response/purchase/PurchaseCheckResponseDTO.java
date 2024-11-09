package store.global.dto.response.purchase;

import java.util.List;

public record PurchaseCheckResponseDTO(
        List<PromotionalProductInfo> promotionalPurchaseProductInfos,
        List<NonPromotionalProductInfo> nonPromotionalProductInfos) {
}
