package store.front.builder.output;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.global.dto.response.display.ProductInfo;

class ProductInfoMessageBuilderTest {

    @Test
    @DisplayName("상품 구매 전, 상품 목록을 출력하기 위한 메시지 생성 테스트")
    void 상품_목록_출력_메시지_생성() {
        //given
        List<ProductInfo> productInfos = List.of(new ProductInfo("콜라", 1000, 1, "할인"),
                new ProductInfo("사이다", 1000, 0, "null"));
        //when
        String builtMessage = ProductInfoMessageBuilder.build(productInfos);
        //then
        assertThat(builtMessage).contains("- 콜라 1,000원 1개 할인", "- 사이다 1,000원 재고 없음");
    }

}