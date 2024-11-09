package store.back.service.product;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.global.dto.request.purchase.PurchaseProductInfo;
import store.global.exception.InvalidProductNameException;
import store.global.exception.OutOfStockException;

class StockValidationServiceTest {

    private final StockValidationService stockValidationService = new StockValidationService();

    @Test
    @DisplayName("구매할 상품이 존재하지 않을 때 예외 발생")
    void 구매상품_존재하지않음_예외_테스트() {
        //given
        List<PurchaseProductInfo> purchaseProductInfos = List.of(new PurchaseProductInfo("햄버거", 12));
        //when
        assertThatThrownBy(() -> stockValidationService.validate(purchaseProductInfos)).isInstanceOf(
                InvalidProductNameException.class);
    }

    @Test
    @DisplayName("구매할 상품의 수량을 넘었을 때 예외 발생")
    void 구매수량_초과_예외_테스트() {
        //given
        List<PurchaseProductInfo> purchaseProductInfos = List.of(new PurchaseProductInfo("콜라", 22));
        //when
        assertThatThrownBy(() -> stockValidationService.validate(purchaseProductInfos)).isInstanceOf(
                OutOfStockException.class);
    }

    @Test
    @DisplayName("구매할 상품이 존재하고 수량이 적합할 때 아무일도 발생하지 않음")
    void 구매상품_존재_수량_존재_테스트() {
        //given
        List<PurchaseProductInfo> purchaseProductInfos = List.of(
                new PurchaseProductInfo("콜라", 5),
                new PurchaseProductInfo("물", 2));
        //when
        assertThatCode(() -> stockValidationService.validate(purchaseProductInfos)).doesNotThrowAnyException();
    }
}