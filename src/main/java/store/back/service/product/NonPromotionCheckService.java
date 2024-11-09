package store.back.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.back.domain.Product;
import store.global.dto.request.purchase.PurchaseProductInfo;
import store.global.dto.response.purchase.NonPromotionalProductInfo;

public class NonPromotionCheckService {
    private final ProductQueryService productQueryService = new ProductQueryService();

    /**
     * 구매하고자 하는 상품들에 프로모션 재고가 부족하여 정가로 구매해야하는 상품 정보(이름, 수량)들을 반환
     * (구매수량) / (buy+get) - (재고수량) / (buy+get) 이 1 이상 이거나
     * (구매수량) / (buy+get) - (재고수량) / (buy+get) 이 0이면서 (구매수량 % (buy+get))이 1 이상이여야 함
     * 이러한 경우 ((구매 수량) / (byt+get)) * (buy+get) + 구매 수량 % (buy+get)를 정가로 구매해야함.
     *
     * @param purchaseProductInfos
     * @return 정가로 구매해야하는 상품 정보를 리스트로 반환, 빈 리스트 반환시 그러한 상품이 존재하지 않음
     */
    public List<NonPromotionalProductInfo> getNonPromotionalProductInfos(List<PurchaseProductInfo> purchaseProductInfos) {
        List<NonPromotionalProductInfo> nonPromotionalProductInfos = new ArrayList<>();

        purchaseProductInfos.forEach(purchaseProductInfo -> {
            Optional<NonPromotionalProductInfo> fullPriceProductInfo = getNonPromotionalProductInfo(purchaseProductInfo);
            fullPriceProductInfo.ifPresent(nonPromotionalProductInfos::add);
        });
        return nonPromotionalProductInfos.stream().toList();
    }

    private Optional<NonPromotionalProductInfo> getNonPromotionalProductInfo(PurchaseProductInfo purchaseProductInfo) {
        Optional<Product> productWithPromotion = productQueryService
                .findProductWithPromotion(purchaseProductInfo.name());

        if (productWithPromotion.isEmpty()) {
            return Optional.empty();
        }
        return calculateNonPromotionalProductInfo(productWithPromotion.get(), purchaseProductInfo);
    }

    private Optional<NonPromotionalProductInfo> calculateNonPromotionalProductInfo(Product product,
                                                                                   PurchaseProductInfo purchaseProductInfo) {
        Integer buyAndGet = product.getPromotion().getBuy() + product.getPromotion().getGet();
        int productInfoPromotionCount = purchaseProductInfo.quantity() / buyAndGet - product.getQuantity() / buyAndGet;
        int quantityAfterPromotion = purchaseProductInfo.quantity() % buyAndGet;
        // 현재 프로모션 재고를 넘치지 않는 경우
        if (productInfoPromotionCount < 0 || (productInfoPromotionCount == 0 && quantityAfterPromotion == 0)) {
            return Optional.empty();
        }
        int fullPricePurchaseQuantity = productInfoPromotionCount * buyAndGet + quantityAfterPromotion;
        return Optional.of(new NonPromotionalProductInfo(purchaseProductInfo.name(), fullPricePurchaseQuantity));
    }
}
