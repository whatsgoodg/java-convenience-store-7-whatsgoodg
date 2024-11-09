package store.back.service.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.back.domain.Product;
import store.back.domain.Promotion;

class ProductQueryServiceTest {

    private final ProductQueryService productQueryService = new ProductQueryService();

    @ParameterizedTest
    @MethodSource("findByNameParams")
    @DisplayName("findByName() 매서드 테스트")
    void 이름을_사용하여_프로덕트_객체_반환(String name, Product product1, Product product2) {
        //given
        //when
        List<Product> products = productQueryService.findByName(name);
        //then
        assertThat(products.size()).isEqualTo(2);
        assertThat(products).usingRecursiveFieldByFieldElementComparator().contains(product1, product2);
    }

    private static Stream<Arguments> findByNameParams() {
        return Stream.of(Arguments.of("콜라", new Product(1, "콜라", 1000, 10,
                        new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"))),
                new Product(2, "콜라", 1000, 10,
                        new Promotion("null", 1, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-01")))));
    }

    @ParameterizedTest
    @MethodSource("productWithPromotionParams")
    @DisplayName("findProductWithPromotion() 매서드를 통해 프로모션이 존재하는 Product 객체 반환 테스트")
    void 이름을_사용하여_프로모션이_존재하는_프로덕트_객체_반환(String name, Product product) {
        //given
        //when
        Optional<Product> productsWithPromotion = productQueryService.findProductWithPromotion(name);
        //then
        assertThat(productsWithPromotion.isPresent()).isTrue();
        assertThat(productsWithPromotion.get()).usingRecursiveComparison().isEqualTo(product);
    }

    private static Stream<Arguments> productWithPromotionParams() {
        return Stream.of(Arguments.of("콜라", new Product(1, "콜라", 1000, 10,
                new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")))));
    }

    @ParameterizedTest
    @MethodSource("noPromotionProductParams")
    @DisplayName("findProductWithPromotion() 매서드를 통해 프로모션이 없을 경우 빈 Optional 객체 반환 테스트")
    void 이름을_사용하여_프로모션이_없을경우_빈_Optioanl_반환(String name, Product product) {
        //given
        //when
        Optional<Product> productsWithPromotion = productQueryService.findProductWithPromotion(name);
        //then
        assertThat(productsWithPromotion.isPresent()).isFalse();
    }

    private static Stream<Arguments> noPromotionProductParams() {
        return Stream.of(Arguments.of("물", new Product(7, "물", 500, 10,
                new Promotion("null", 1, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-01")))));
    }

}