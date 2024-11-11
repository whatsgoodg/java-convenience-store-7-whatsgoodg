package store.back.service.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.global.dto.request.purchase.PurchaseProductInfo;
import store.global.dto.response.purchase.PromotionalProductInfo;

class PromotionCheckServiceTest {

    private final PromotionCheckService promotionCheckService = new PromotionCheckService();

    @ParameterizedTest
    @MethodSource("promotionalProductsParams")
    @DisplayName("구매한 수량에 대해 증정을 받을 수 있을 때 받을 수 있는 상품 정보 반환")
    void 프로모션으로_증정_가능한_상품정보_반환(final List<PurchaseProductInfo> purchaseProductInfos,
                               final List<PromotionalProductInfo> expectedPromotionalProductInfos) {
        //given
        List<PromotionalProductInfo> promotionalProductInfos = promotionCheckService.getPromotionalProductInfos(
                purchaseProductInfos);
        //when
        assertThat(promotionalProductInfos).usingRecursiveFieldByFieldElementComparator()
                .containsAll(expectedPromotionalProductInfos);
    }

    private static Stream<Arguments> promotionalProductsParams() {
        return Stream.of(
                Arguments.of(
                        List.of(new PurchaseProductInfo("콜라", 8)),
                        List.of(new PromotionalProductInfo("콜라", 1))),
                Arguments.of(
                        List.of(new PurchaseProductInfo("콜라", 8),
                                new PurchaseProductInfo("사이다", 6)),
                        List.of(new PromotionalProductInfo("콜라", 1))),
                Arguments.of(
                        List.of(new PurchaseProductInfo("콜라", 8),
                                new PurchaseProductInfo("사이다", 5)),
                        List.of(new PromotionalProductInfo("콜라", 1),
                                new PromotionalProductInfo("사이다", 1))),
                Arguments.of(
                        List.of(new PurchaseProductInfo("콜라", 8),
                                new PurchaseProductInfo("사이다", 5),
                                new PurchaseProductInfo("오렌지주스", 5)),
                        List.of(new PromotionalProductInfo("콜라", 1),
                                new PromotionalProductInfo("사이다", 1),
                                new PromotionalProductInfo("오렌지주스", 1))),
                Arguments.of(
                        List.of(new PurchaseProductInfo("물", 5)),
                        List.of()),
                Arguments.of(
                        List.of(new PurchaseProductInfo("물", 5),
                                new PurchaseProductInfo("콜라", 8)),
                        List.of(new PromotionalProductInfo("콜라", 1))));
    }
}