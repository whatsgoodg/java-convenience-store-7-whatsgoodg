package store.global.dto.request;

import java.util.List;

public record PurchaseCheckRequestDTO(List<PurchaseProductInfo> purchaseProductInfos) {
}
