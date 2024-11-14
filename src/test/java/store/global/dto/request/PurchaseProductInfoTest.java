package store.global.dto.request;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.global.dto.request.purchase.PurchaseProductInfo;
import store.global.exception.InvalidPurchaseQuantityException;

class PurchaseProductInfoTest {

    @Test
    @DisplayName("구매할 수량이 1 미만일 때 예외 발생")
    void 구매할_상품_1미만_예외_테스트() {
        //given
        String name = "콜라";
        Integer quantity = 0;
        //when
        //then
        assertThatThrownBy(() -> new PurchaseProductInfo(name, quantity)).isInstanceOf(
                InvalidPurchaseQuantityException.class);
    }
}