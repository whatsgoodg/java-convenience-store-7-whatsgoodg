package store.front.parser.input;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.global.dto.request.purchase.PurchaseProductInfo;
import store.global.exception.InvalidProductInputException;

class PurchaseProductInfoParserTest {

    @Test
    @DisplayName("상품 구매 입력값에 따라 파싱을 수행 후 PurchaseProducInfo 반환")
    void 상품_구매_입력_파싱_테스트1() {
        //given
        String input = "[콜라-5],[사이다-1]";
        List<PurchaseProductInfo> purchaseProductInfos = List.of(new PurchaseProductInfo("콜라", 5),
                new PurchaseProductInfo("사이다", 1));
        //when
        List<PurchaseProductInfo> parse = PurchaseProductInfoParser.parse(input);
        //then
        assertThat(parse).containsAll(purchaseProductInfos);
    }

    @Test
    @DisplayName("상품 구매 입력값이 올바르지 않을 경우 예외 반환")
    void 상품_구매_입력_파싱_예외_테스트1() {
        //given
        String input = ",[콜라-5],[사이다-1]";
        //when
        //then
        assertThatThrownBy(() -> PurchaseProductInfoParser.parse(input)).isInstanceOf(
                InvalidProductInputException.class);
    }

    @Test
    @DisplayName("상품 구매 입력값이 한국어가 아닐 경우 예외 반환")
    void 상품_구매_입력_파싱_예외_테스트2() {
        //given
        String input = "[asd-5],[사이다-1]";
        //when
        //then
        assertThatThrownBy(() -> PurchaseProductInfoParser.parse(input)).isInstanceOf(
                InvalidProductInputException.class);
    }

    @Test
    @DisplayName("상품 구매 입력값의 수량이 숫자가 아닐 경우 예외 반환")
    void 상품_구매_입력_파싱_예외_테스트3() {
        //given
        String input = "[콜라-a],[사이다-1]";
        //when
        //then
        assertThatThrownBy(() -> PurchaseProductInfoParser.parse(input)).isInstanceOf(
                InvalidProductInputException.class);
    }
}