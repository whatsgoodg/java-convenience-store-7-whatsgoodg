package store.front.builder.input;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.global.dto.response.purchase.PromotionalProductInfo;

class PromotionalProductMessageBuilderTest {

    @Test
    @DisplayName("프로모션이 적용되는 상품에 대해 입력 메시지 생성 테스트")
    void 프로모션_증정_상품_입력_메시지_생성() {
        //given
        List<PromotionalProductInfo> promotionalProductInfo = List.of(new PromotionalProductInfo("콜라", 1));
        //when
        String builtMessage = PromotionalProductMessageBuilder.build(promotionalProductInfo);
        //then
        assertThat(builtMessage).contains("현재 콜라은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
    }
}