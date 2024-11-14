package store.back.repository.loader;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.back.domain.product.Product;
import store.back.domain.product.Promotion;

class ProductFileLoaderTest {

    @ParameterizedTest
    @MethodSource("productParams")
    @DisplayName("products.md 파일을 읽어 List<Product>를 반환하는 테스트")
    void 파일에서_객체생성_테스트(Product product1, Product product2) {
        //given
        //when
        Map<Integer, Product> products = ProductFileLoader.loadProducts();
        //then
        assertThat(products.size()).isEqualTo(18);
        assertThat(products.get(1)).usingRecursiveComparison().isEqualTo(product1);
        assertThat(products.get(18)).usingRecursiveComparison().isEqualTo(product2);
    }

    private static Stream<Arguments> productParams() {
        return Stream.of(
                Arguments.of(
                        new Product(1, "콜라", 1000, 10,
                                new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"),
                                        LocalDate.parse("2024-12-31"))),
                        new Product(18, "컵라면", 1700, 10,
                                new Promotion("null", 1, 1, LocalDate.parse("2024-01-01"),
                                        LocalDate.parse("2024-01-01")))));
    }
}