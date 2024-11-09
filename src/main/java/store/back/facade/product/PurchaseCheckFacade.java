package store.back.facade.product;

import java.util.List;
import store.back.service.product.FullPriceCheckService;
import store.back.service.product.PromotionCheckService;
import store.back.service.product.StockValidationService;
import store.global.dto.request.PurchaseCheckRequestDTO;
import store.global.dto.request.PurchaseProductInfo;
import store.global.dto.response.FullPriceProductInfo;
import store.global.dto.response.PromotionalProductInfo;
import store.global.dto.response.PurchaseCheckResponseDTO;

public class PurchaseCheckFacade {
    private final PromotionCheckService promotionCheckService = new PromotionCheckService();
    private final FullPriceCheckService fullPriceCheckService = new FullPriceCheckService();
    private final StockValidationService stockValidationService = new StockValidationService();

    public PurchaseCheckResponseDTO checkPurchaseConditions(PurchaseCheckRequestDTO purchaseCheckRequestDTO) {
        List<PurchaseProductInfo> purchaseProductInfos = purchaseCheckRequestDTO.purchaseProductInfos();
        stockValidationService.validate(purchaseProductInfos);

        List<PromotionalProductInfo> promotionalPurchaseProductInfos = promotionCheckService.getPromotionalProductInfos(
                purchaseProductInfos);
        List<FullPriceProductInfo> fullPricePurchaseProductInfos = fullPriceCheckService.getFullPriceProductInfos(
                purchaseProductInfos);

        return new PurchaseCheckResponseDTO(promotionalPurchaseProductInfos, fullPricePurchaseProductInfos);
    }
}
