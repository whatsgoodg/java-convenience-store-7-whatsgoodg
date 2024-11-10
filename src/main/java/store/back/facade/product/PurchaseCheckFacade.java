package store.back.facade.product;

import java.util.List;
import store.back.service.product.NonPromotionCheckService;
import store.back.service.product.PromotionCheckService;
import store.back.service.product.StockValidationService;
import store.global.dto.request.purchase.PurchaseCheckRequestDTO;
import store.global.dto.request.purchase.PurchaseProductInfo;
import store.global.dto.response.purchase.NonPromotionalProductInfo;
import store.global.dto.response.purchase.PromotionalProductInfo;
import store.global.dto.response.purchase.PurchaseCheckResponseDTO;

public class PurchaseCheckFacade {
    private final PromotionCheckService promotionCheckService = new PromotionCheckService();
    private final NonPromotionCheckService nonPromotionCheckService = new NonPromotionCheckService();
    private final StockValidationService stockValidationService = new StockValidationService();

    public PurchaseCheckResponseDTO checkPurchaseConditions(PurchaseCheckRequestDTO purchaseCheckRequestDTO) {
        List<PurchaseProductInfo> purchaseProductInfos = purchaseCheckRequestDTO.purchaseProductInfos();
        stockValidationService.validatePurchasable(purchaseProductInfos);

        List<PromotionalProductInfo> promotionalPurchaseProductInfos = promotionCheckService.getPromotionalProductInfos(
                purchaseProductInfos);
        List<NonPromotionalProductInfo> nonPromotionalProductInfos = nonPromotionCheckService.getNonPromotionalProductInfos(
                purchaseProductInfos);

        return new PurchaseCheckResponseDTO(promotionalPurchaseProductInfos, nonPromotionalProductInfos);
    }
}
