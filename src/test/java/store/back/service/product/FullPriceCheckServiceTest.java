package store.back.service.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.global.dto.request.PurchaseProductInfo;
import store.global.dto.response.FullPriceProductInfo;

class FullPriceCheckServiceTest {
    private final FullPriceCheckService fullPriceCheckService = new FullPriceCheckService();

    @ParameterizedTest
    @MethodSource("fullPriceProductInfoParams")
    @DisplayName("상품을 구매할 때, 프로모션이 존재하는 상품은 있지만 수량이 적어 정가로 구매해야하는 상품을 반환하는 테스트")
    void 상품_정가_구매_정보_반환_테스트(List<PurchaseProductInfo> purchaseProductInfos,
                            List<FullPriceProductInfo> expectedFullPriceProductInfos) {
        //given
        //when
        List<FullPriceProductInfo> fullPriceProductInfos = fullPriceCheckService.getFullPriceProductInfos(
                purchaseProductInfos);
        //then
        assertThat(fullPriceProductInfos).usingRecursiveFieldByFieldElementComparator()
                .containsAll(expectedFullPriceProductInfos);
    }

    private static Stream<Arguments> fullPriceProductInfoParams() {
        return Stream.of(
                Arguments.of(
                        List.of(new PurchaseProductInfo("콜라", 6)),
                        List.of()
                ),
                Arguments.of(
                        List.of(new PurchaseProductInfo("감자칩", 6)),
                        List.of(new FullPriceProductInfo("감자칩", 2))
                ),
                Arguments.of(
                        List.of(new PurchaseProductInfo("감자칩", 6),
                                new PurchaseProductInfo("콜라", 13)),
                        List.of(new FullPriceProductInfo("감자칩", 2),
                                new FullPriceProductInfo("콜라", 4))
                ),
                Arguments.of(
                        List.of(new PurchaseProductInfo("물", 6)),
                        List.of()
                ),
                Arguments.of(
                        List.of(new PurchaseProductInfo("물", 6),
                                new PurchaseProductInfo("콜라", 13)),
                        List.of(new FullPriceProductInfo("콜라", 4))
                ),
                Arguments.of(
                        List.of(new PurchaseProductInfo("컵라면", 10),
                                new PurchaseProductInfo("정식도시락", 8),
                                new PurchaseProductInfo("오렌지주스", 9)),
                        List.of(new FullPriceProductInfo("컵라면", 10),
                                new FullPriceProductInfo("오렌지주스", 1))
                )
        );
    }
}