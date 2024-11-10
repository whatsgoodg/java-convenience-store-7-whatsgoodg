package store.front.view.input;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import java.util.Map;
import store.front.builder.input.NonPromotionalProductMessageBuilder;
import store.front.builder.input.PromotionalProductMessageBuilder;
import store.front.parser.input.PurchaseProductInfoParser;
import store.front.parser.input.YNParser;
import store.front.repository.ShoppingCart;
import store.global.dto.request.purchase.PurchaseCheckRequestDTO;
import store.global.dto.request.purchase.PurchaseProductInfo;
import store.global.dto.request.order.OrderProductInfo;
import store.global.dto.request.order.OrderRequestDTO;
import store.global.dto.response.purchase.NonPromotionalProductInfo;
import store.global.dto.response.purchase.PromotionalProductInfo;
import store.global.dto.response.purchase.PurchaseCheckResponseDTO;
import store.global.enums.InputMessage;

public class InputView {
    private final ShoppingCart shoppingCart = new ShoppingCart();

    public PurchaseCheckRequestDTO readPurchaseProductInfos() {
        System.out.println(InputMessage.PURCHASE.getMessage());
        String readProductInfos = Console.readLine();
        System.out.println();

        List<PurchaseProductInfo> purchaseProductInfos = PurchaseProductInfoParser.parse(readProductInfos);
        purchaseProductInfos.forEach(
                purchaseProductInfo -> shoppingCart.save(purchaseProductInfo.name(), purchaseProductInfo.quantity()));

        return new PurchaseCheckRequestDTO(purchaseProductInfos);
    }

    /**
     * 사용자에게 프로모션 적용 상품 추가 구매, 프로모션 미적용 정가 구매를 물어봄. 이에 따라 장바구니에서 수량을 제외하거나 추가
     *
     * @param purchaseCheckResponseDTO
     * @return 총 구매 상품의 수량 프로모션 미적용에 대해, 프로모션 미적용 상품을 제외할 경우 총 구매 상품의 수량이 0이 될 수 있음. 해당 경우엔 컨트롤러에서 예외를 던져 구매를 재시작함.
     */
    public Integer promptUserForPromotions(final PurchaseCheckResponseDTO purchaseCheckResponseDTO) {
        promptUserForPromotion(purchaseCheckResponseDTO.promotionalPurchaseProductInfos());
        promptUserForNonPromotion(purchaseCheckResponseDTO.nonPromotionalProductInfos());
        return shoppingCart.getProductInfos().values().stream().mapToInt(value -> value).sum();
    }

    private void promptUserForPromotion(final List<PromotionalProductInfo> promotionalProductInfos) {
        promotionalProductInfos.forEach(promotionalProductInfo -> {
            System.out.println(PromotionalProductMessageBuilder.build(promotionalProductInfo));
            Boolean isYes = readYesOrNo();

            if (isYes) {
                shoppingCart.increaseQuantity(promotionalProductInfo.name(), promotionalProductInfo.quantity());
            }
        });
    }

    private void promptUserForNonPromotion(final List<NonPromotionalProductInfo> nonPromotionalProductInfos) {
        nonPromotionalProductInfos.forEach(nonPromotionalProductInfo -> {
            System.out.println(NonPromotionalProductMessageBuilder.build(nonPromotionalProductInfo));
            Boolean isYes = readYesOrNo();

            if (!isYes) {
                shoppingCart.decreaseQuantity(nonPromotionalProductInfo.name(), nonPromotionalProductInfo.quantity());
            }
        });
    }

    public void promptUserForMemberShip() {
        System.out.println(InputMessage.MEMBERSHIP.getMessage());
        Boolean isYes = readYesOrNo();

        if (isYes) {
            shoppingCart.setMembership();
        }
    }

    public OrderRequestDTO orderProducts() {
        Boolean membership = shoppingCart.getMembership();
        Map<String, Integer> productInfos = shoppingCart.getProductInfos();

        List<OrderProductInfo> orderProductInfos = productInfos.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> new OrderProductInfo(entry.getKey(), entry.getValue())).toList();

        return new OrderRequestDTO(orderProductInfos, membership);
    }

    public Boolean promptRetry() {
        System.out.println(InputMessage.RETRY.getMessage());
        String input = Console.readLine();
        return YNParser.parse(input);
    }

    private Boolean readYesOrNo() {
        String input = Console.readLine();
        System.out.println();
        return YNParser.parse(input);
    }

    public void clearShoppingCart() {
        shoppingCart.clear();
    }
}
