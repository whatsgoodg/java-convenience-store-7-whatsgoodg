package store.back.repository.loader;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.back.domain.product.Promotion;

class PromotionFileLoaderTest {

    @Test
    @DisplayName("promotions.md 파일을 읽어 List<Promotion>으로 반환 테스트")
    void 파일에서_객체생성_테스트() {
        //given
        Promotion promotion1 = new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"),
                LocalDate.parse("2024-12-31"));
        Promotion promotion2 = new Promotion("null", 1, 1, LocalDate.parse("2024-01-01"),
                LocalDate.parse("2024-01-01"));
        //when
        List<Promotion> promotions = PromotionFileLoader.loadPromotions();
        //then
        assertThat(promotions.size()).isEqualTo(4);
        assertThat(promotions).usingRecursiveFieldByFieldElementComparator().contains(promotion1, promotion2);
    }
}