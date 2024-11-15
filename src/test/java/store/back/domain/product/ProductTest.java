package store.back.domain.product;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.global.exception.OutOfStockException;

class ProductTest {

    @ParameterizedTest
    @MethodSource("validPromotionParams")
    @DisplayName("Product 객체 생성 테스트")
    void 객체_생성_테스트(final String promotionName, final Integer buy, final Integer get, final LocalDate startDate,
                   final LocalDate endDate) {
        //given
        String name = "asd";
        Integer price = 1000;
        Integer quantity = 1000;
        //when
        Promotion promotion = new Promotion(promotionName, buy, get, startDate, endDate);
        Product product = new Product(1, name, price, quantity, promotion);
        //then
        assertThat(product.getId()).isEqualTo(1);
        assertThat(product.getName()).isEqualTo("asd");
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getQuantity()).isEqualTo(1000);
        assertThat(product.getQuantity()).isEqualTo(1000);
        assertThat(product.getPromotion()).isEqualTo(promotion);
    }

    @ParameterizedTest
    @MethodSource("validPromotionParams")
    @DisplayName("Product decreaseQuantity 테스트")
    void 수량_감소_테스트(final String promotionName, final Integer buy, final Integer get, final LocalDate startDate,
                   final LocalDate endDate) {
        //given
        String name = "asd";
        Integer price = 1000;
        Integer quantity = 1000;
        //when
        Product product = new Product(1, name, price, quantity,
                new Promotion(promotionName, buy, get, startDate, endDate));
        product.decreaseQuantity(1000);
        //then
        assertThat(product.getQuantity()).isEqualTo(0);
    }

    @ParameterizedTest
    @MethodSource("validPromotionParams")
    @DisplayName("Product decreaseQuantity 예외 테스트")
    void 수량_감소_예외_테스트(final String promotionName, final Integer buy, final Integer get, final LocalDate startDate,
                      final LocalDate endDate) {
        //given
        String name = "asd";
        Integer price = 1000;
        Integer quantity = 1000;
        Product product = new Product(1, name, price, quantity,
                new Promotion(promotionName, buy, get, startDate, endDate));
        //when
        //then
        assertThatThrownBy(() -> product.decreaseQuantity(1001)).isInstanceOf(OutOfStockException.class);
    }

    private static Stream<Arguments> validPromotionParams() {
        return Stream.of(Arguments.of("asd", 1, 1, "2024-11-01", "2024-11-01"));
    }
}