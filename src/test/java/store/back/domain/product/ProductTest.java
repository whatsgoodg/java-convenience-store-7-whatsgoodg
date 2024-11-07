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
    void 객체_생성_테스트(String promotionName, Integer buy, Integer get, LocalDate startDate, LocalDate endDate) {
        //given
        String name = "asd";
        Integer price = 1000;
        Integer quantity = 1000;
        //when
        Product product = new Product(name, price, quantity,
                new Promotion(promotionName, buy, get, startDate, endDate));
        Promotion promotion = product.getPromotion();
        //then
        assertThat(product.getName()).isEqualTo("asd");
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getQuantity()).isEqualTo(1000);
        assertThat(product.getQuantity()).isEqualTo(1000);
        // promotion
        assertThat(promotion.getName()).isEqualTo("asd");
        assertThat(promotion.getBuy()).isEqualTo(1);
        assertThat(promotion.getGet()).isEqualTo(1);
        assertThat(promotion.getStartDate()).isEqualTo(LocalDate.parse("2024-11-01"));
        assertThat(promotion.getEndDate()).isEqualTo(LocalDate.parse("2024-11-01"));
    }

    @ParameterizedTest
    @MethodSource("validPromotionParams")
    @DisplayName("Product decreaseQuantity 테스트")
    void 수량_감소_테스트(String promotionName, Integer buy, Integer get, LocalDate startDate, LocalDate endDate) {
        //given
        String name = "asd";
        Integer price = 1000;
        Integer quantity = 1000;
        //when
        Product product = new Product(name, price, quantity,
                new Promotion(promotionName, buy, get, startDate, endDate));
        product.decreaseQuantity(1000);
        //then
        assertThat(product.getQuantity()).isEqualTo(0);
    }

    @ParameterizedTest
    @MethodSource("validPromotionParams")
    @DisplayName("Product decreaseQuantity 예외 테스트")
    void 수량_감소_예외_테스트(String promotionName, Integer buy, Integer get, LocalDate startDate, LocalDate endDate) {
        //given
        String name = "asd";
        Integer price = 1000;
        Integer quantity = 1000;
        Product product = new Product(name, price, quantity,
                new Promotion(promotionName, buy, get, startDate, endDate));
        //when
        //then
        assertThatThrownBy(() -> product.decreaseQuantity(1001)).isInstanceOf(OutOfStockException.class);
    }

    private static Stream<Arguments> validPromotionParams() {
        return Stream.of(Arguments.of("asd", 1, 1, "2024-11-01", "2024-11-01"));
    }
}