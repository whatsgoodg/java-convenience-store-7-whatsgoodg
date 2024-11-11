package store.back.service.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.back.domain.order.OrderLine;
import store.back.domain.order.OrderLineStatus;
import store.back.domain.product.Product;
import store.back.repository.ProductRepository;

class OrderServiceTest {
    private final ProductRepository productRepository = new ProductRepository();
    private final OrderService orderService = new OrderService();

    @AfterEach
    void setUp(){
        ProductRepository.loadAgain();
    }

    @ParameterizedTest
    @MethodSource("orderParams")
    @DisplayName("주문을 수행하고 재고에 변경이 존재하는지 확인")
    void 구매_이후_재고_변경_테스트(final List<OrderLine> orderLines, final Integer id, final Integer quantity) {
        //given
        //when
        orderService.orderProducts(orderLines);
        Optional<Product> findProduct = productRepository.findById(id);
        //then
        assertThat(findProduct.isPresent()).isTrue();
        assertThat(findProduct.get().getQuantity()).isEqualTo(quantity);
    }

    private static Stream<Arguments> orderParams() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new OrderLine("콜라", 4, 4000, OrderLineStatus.PROMOTION),
                                new OrderLine("콜라", 4, 4000, OrderLineStatus.FREEBIE)),
                        1,
                        2),
                Arguments.of(
                        List.of(
                                new OrderLine("물", 10, 5000, OrderLineStatus.NONE)),
                        9,
                        0),
                Arguments.of(
                        List.of(
                                new OrderLine("오렌지주스", 4, 7200, OrderLineStatus.PROMOTION),
                                new OrderLine("오렌지주스", 4, 7200, OrderLineStatus.FREEBIE)),
                        5,
                        1),
                Arguments.of(
                        List.of(
                                new OrderLine("컵라면", 2, 3400, OrderLineStatus.NONE)),
                        17,
                        0),
                Arguments.of(
                        List.of(
                                new OrderLine("컵라면", 2, 3400, OrderLineStatus.MEMBERSHIP)),
                        18,
                        9));
    }
}