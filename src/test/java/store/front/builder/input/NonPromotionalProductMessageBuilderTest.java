package store.front.builder.input;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.global.dto.response.purchase.NonPromotionalProductInfo;

class NonPromotionalProductMessageBuilderTest {

    @Test
    @DisplayName("정가로 구매해야하는 상품에 대해 입력 메시지 생성 테스트")
    void 정가_구매_상품_입력_메시지_생성() {
        //given
        NonPromotionalProductInfo nonPromotionalProductInfos = new NonPromotionalProductInfo("사이다", 2);
        //when
        String builtMessage = NonPromotionalProductMessageBuilder.build(nonPromotionalProductInfos);
        //then
        assertThat(builtMessage).contains("현재 사이다 2개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
    }
}