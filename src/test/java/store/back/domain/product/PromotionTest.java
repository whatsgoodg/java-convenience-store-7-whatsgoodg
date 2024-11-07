package store.back.domain.product;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PromotionTest {

    @Test
    @DisplayName("List<String> 타입으로부터 객체를 생성")
    void 객체_생성_테스트() {
        //given
        List<String> fields = List.of("asd", "1", "1", "2024-11-01", "2024-11-01");
        //when
        Promotion promotion = Promotion.from(fields);
        //then
        assertThat(promotion.getName()).isEqualTo("asd");
        assertThat(promotion.getBuy()).isEqualTo(1);
        assertThat(promotion.getGet()).isEqualTo(1);
        assertThat(promotion.getStartDate()).isEqualTo(LocalDate.parse("2024-11-01"));
        assertThat(promotion.getEndDate()).isEqualTo(LocalDate.parse("2024-11-01"));
    }

    @Test
    @DisplayName("List<String> 타입으로부터 객체 생성 시 field의 개수가 적을 경우 예외 발생")
    void 객체_생성_예외_테스트1() {
        //given
        List<String> fields = List.of("asd", "1", "1", "2024-11-01");
        //when
        //then
        assertThatThrownBy(() -> Promotion.from(fields)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("startDate가 endDate보다 더 나중일 때 예외 발생")
    void 객체_생성_예외_테스트2() {
        //given
        List<String> fields = List.of("asd", "1", "1", "2024-11-02", "2024-11-01");
        //when
        //then
        assertThatThrownBy(() -> Promotion.from(fields)).isInstanceOf(IllegalArgumentException.class);
    }
}