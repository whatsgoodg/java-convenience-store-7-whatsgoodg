package store.back.domain;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.*;

class PromotionTest {

    @ParameterizedTest
    @MethodSource("validPromotionParams")
    @DisplayName("List<String> 타입으로부터 객체를 생성")
    void 객체_생성_테스트(String name, Integer buy, Integer get, LocalDate startDate, LocalDate endDate) {
        //given
        //when
        Promotion promotion = new Promotion(name, buy, get, startDate, endDate);
        //then
        assertThat(promotion.getName()).isEqualTo("asd");
        assertThat(promotion.getBuy()).isEqualTo(1);
        assertThat(promotion.getGet()).isEqualTo(1);
        assertThat(promotion.getStartDate()).isEqualTo(LocalDate.parse("2024-11-01"));
        assertThat(promotion.getEndDate()).isEqualTo(LocalDate.parse("2024-11-01"));
    }

    private static Stream<Arguments> validPromotionParams() {
        return Stream.of(Arguments.of("asd", 1, 1, "2024-11-01", "2024-11-01"));
    }

    @ParameterizedTest
    @MethodSource("invalidPromotionParams")
    @DisplayName("startDate가 endDate보다 더 나중일 때 예외 발생")
    void 객체_생성_예외_테스트2(String name, Integer buy, Integer get, LocalDate startDate, LocalDate endDate) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Promotion(name, buy, get, startDate, endDate)).isInstanceOf(
                IllegalArgumentException.class);
    }

    private static Stream<Arguments> invalidPromotionParams() {
        return Stream.of(Arguments.of("asd", 1, 1, "2024-11-02", "2024-11-01"));
    }
}