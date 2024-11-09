package store.global.dto.request.purchase;

import java.util.List;

public record PurchaseCheckRequestDTO(List<PurchaseProductInfo> purchaseProductInfos) {
}
