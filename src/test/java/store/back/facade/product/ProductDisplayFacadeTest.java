package store.back.facade.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.global.dto.response.display.ProductDisplayResponseDTO;
import store.global.dto.response.display.ProductInfo;

class ProductDisplayFacadeTest {
    private final ProductDisplayFacade productDisplayFacade = new ProductDisplayFacade();

    @Test
    @DisplayName("모든 상품 정보를 반환하는 테스트")
    void 모든_상품_정보_반환() {
        //given
        ProductInfo productInfo = new ProductInfo("콜라", 1000, 10, "탄산2+1");
        //when
        ProductDisplayResponseDTO productDisplayResponseDTO = productDisplayFacade.getAllProducts();
        List<ProductInfo> productInfos = productDisplayResponseDTO.productInfos();
        //then
        assertThat(productInfos.size()).isEqualTo(18);
        assertThat(productInfos).usingRecursiveFieldByFieldElementComparator().contains(productInfo);
    }
}