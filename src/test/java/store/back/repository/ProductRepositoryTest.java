package store.back.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.back.domain.Product;
import store.back.domain.Promotion;

class ProductRepositoryTest {

    private final ProductRepository productRepository = new ProductRepository();

    @ParameterizedTest
    @MethodSource("findAllParams")
    @DisplayName("findAll() 매서드를 통한 모든 객체 불러오기")
    void 모든_객체_반환_테스트(Product product1, Product product2) {
        //given
        //when
        List<Product> products = productRepository.findAll();
        //then
        assertThat(products.size()).isEqualTo(16);
        assertThat(products).usingRecursiveFieldByFieldElementComparator().contains(product1, product2);
    }

    private static Stream<Arguments> findAllParams() {
        return Stream.of(Arguments.of(
                new Product(1, "콜라", 1000, 10,
                        new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"))),
                new Product(16, "컵라면", 1700, 10,
                        new Promotion("null", 1, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-01")))));
    }

    @ParameterizedTest
    @MethodSource("findByNameParams")
    @DisplayName("findByName() 매서드를 통한 특정 객체 불러오기")
    void 이름을_통한_객체_반환_테스트1(Product product1, Product product2) {
        //given
        String name = "콜라";
        //when
        List<Product> products = productRepository.findByName(name);
        //then
        assertThat(products.size()).isEqualTo(2);
        assertThat(products).usingRecursiveFieldByFieldElementComparator().contains(product1, product2);
    }

    private static Stream<Arguments> findByNameParams() {
        return Stream.of(Arguments.of(
                new Product(1, "콜라", 1000, 10,
                        new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"))),
                new Product(2, "콜라", 1000, 10,
                        new Promotion("null", 1, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-01")))));
    }

    @Test
    @DisplayName("존재하지 않는 이름의 Product 객체를 findByName() 매서드를 통해 가져올 때 빈 리스트 반환")
    void 이름을_통한_객체_반환_테스트2() {
        //given
        String name = "닥터페퍼";
        //when
        List<Product> products = productRepository.findByName(name);
        //then
        assertThat(products.isEmpty()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("saveParams")
    @DisplayName("save() 매서드를 통한 특정 객체 저장")
    void 객쳬_저장_테스트(Product product) {
        //given
        //when
        product.decreaseQuantity(5);
        Product savedProduct = productRepository.save(product);
        //then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct).usingRecursiveComparison().isEqualTo(product);
    }

    private static Stream<Arguments> saveParams() {
        return Stream.of(Arguments.of(
                new Product(1, "콜라", 1000, 10,
                new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")))));
    }

    @ParameterizedTest
    @MethodSource("saveExceptionParams")
    @DisplayName("저장하려는 Product 객체의 특정 id가 존재하지 않을 때 예외 발생")
    void 객쳬_저장_예외_테스트(Product product) {
        //given
        //when
        //then
        assertThatThrownBy(() -> productRepository.save(product)).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> saveExceptionParams() {
        return Stream.of(Arguments.of(
                new Product(19, "콜라", 1000, 10,
                new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")))));
    }

    @ParameterizedTest
    @MethodSource("findByIdParams")
    @DisplayName("findById() 매서드를 통한 특정 불러오기")
    void 아이디를_통한_객체_반환_테스트(Product product) {
        //given
        //when
        Optional<Product> findProduct = productRepository.findById(product.getId());
        //then
        assertThat(findProduct.isPresent()).isTrue();
        assertThat(findProduct.get()).usingRecursiveComparison().isEqualTo(product);
    }

    private static Stream<Arguments> findByIdParams() {
        return Stream.of(Arguments.of(
                new Product(1, "콜라", 1000, 10,
                new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")))));
    }

    @Test
    @DisplayName("존재하지 않는 id를 통해 findById() 호출 시 빈 Optioanl 객체 반환")
    void 빈_Optional_객체_반환() {
        //given
        //when
        Optional<Product> findProduct = productRepository.findById(20);
        //then
        assertThat(findProduct.isEmpty()).isTrue();
    }
}