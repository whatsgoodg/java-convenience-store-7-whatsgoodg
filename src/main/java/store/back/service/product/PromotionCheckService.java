package store.back.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.back.domain.Product;
import store.global.dto.request.purchase.PurchaseProductInfo;
import store.global.dto.response.purchase.PromotionalProductInfo;

public class PromotionCheckService {
    private final ProductQueryService productQueryService = new ProductQueryService();

    /**
     * 모든 PurchaseProductInfos 구매하고자하는 상품에 대해 추가로 증정받을 수 있는 상품 정보(상품 이름, 수량)들을 반환
     * (구매 수량) % (buy+get)이 buy와 같으면서, 상품 재고 수량이 구매 수량 + get보다 크거나 같을 경우 증정 가능
     *
     * @param purchaseProductInfos 구매하고자 하는 상품 정보
     * @return 추가로 증정받을 수 있는 상품 정보를 List로 반환, 빈 리스트 반환 시 그러한 상품이 존재하지 않음
     */
    public List<PromotionalProductInfo> getPromotionalProductInfos(List<PurchaseProductInfo> purchaseProductInfos) {
        List<PromotionalProductInfo> promotionalProductInfos = new ArrayList<>();
        purchaseProductInfos.forEach(purchaseProductInfo -> {
            Optional<PromotionalProductInfo> promotionalProductInfo = getPromotionalProductInfo(purchaseProductInfo);
            promotionalProductInfo.ifPresent(promotionalProductInfos::add);
        });
        return promotionalProductInfos.stream().toList();
    }

    private Optional<PromotionalProductInfo> getPromotionalProductInfo(PurchaseProductInfo purchaseProductInfo) {
        Optional<Product> productWithPromotion = productQueryService.findProductWithPromotion(
                purchaseProductInfo.name());
        // 프로모션이 존재하는 상품이 없으면 증정받을 수 있는 것이 없음
        if (productWithPromotion.isEmpty()) {
            return Optional.empty();
        }
        Product product = productWithPromotion.get();
        return calculatePromotionalProductInfo(product, purchaseProductInfo.quantity());
    }

    private Optional<PromotionalProductInfo> calculatePromotionalProductInfo(Product product, Integer quantity) {
        Integer buy = product.getPromotion().getBuy();
        Integer get = product.getPromotion().getGet();
        int buyAfterPromotion = quantity % (buy + get);
        int addedQuantity = quantity + get;
        // 프로모션 적용 후 구매한 수량이 buy와 같아야함, 그리고 get을 추가로 받았을 때의 수량이 상품 재고보다 작거나 같아야함.
        if (buyAfterPromotion == buy && addedQuantity <= product.getQuantity()) {
            return Optional.of(new PromotionalProductInfo(product.getName(), get));
        }
        return Optional.empty();
    }
}
