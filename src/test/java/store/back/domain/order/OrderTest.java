package store.back.domain.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.back.domain.invoice.FreebieProduct;
import store.back.domain.invoice.Invoice;
import store.back.domain.invoice.OrderedProduct;

class OrderTest {

    @ParameterizedTest
    @MethodSource("orderParams")
    @DisplayName("주문을 실행하고 이에 따른 영수증을 반환")
    void 주문_수행_테스트(List<OrderLine> orderLines, Invoice expecteInvoice) {
        //given
        //when
        Order order = new Order(orderLines);
        Invoice invoice = order.order();
        //then
        assertThat(invoice).usingRecursiveComparison().isEqualTo(expecteInvoice);
    }

    private static Stream<Arguments> orderParams() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new OrderLine("콜라", 4, 4000, OrderLineStatus.PROMOTION),
                                new OrderLine("콜라", 2, 2000, OrderLineStatus.FREEBIE)),
                        new Invoice(List.of(
                                new OrderedProduct("콜라", 6, 6000)),
                                List.of(
                                        new FreebieProduct("콜라", 2)), 6000, 6,
                                2000, 0, 4000)),
                Arguments.of(
                        List.of(
                                new OrderLine("콜라", 4, 4000, OrderLineStatus.PROMOTION),
                                new OrderLine("콜라", 2, 2000, OrderLineStatus.FREEBIE),
                                new OrderLine("물", 5, 5000, OrderLineStatus.NONE)),
                        new Invoice(List.of(
                                new OrderedProduct("물", 5, 5000),
                                new OrderedProduct("콜라", 6, 6000)),
                                List.of(
                                        new FreebieProduct("콜라", 2)), 11000, 11,
                                2000, 0, 9000)),
                Arguments.of(
                        List.of(
                                new OrderLine("콜라", 4, 4000, OrderLineStatus.PROMOTION),
                                new OrderLine("콜라", 2, 2000, OrderLineStatus.FREEBIE),
                                new OrderLine("초코바", 2, 2400, OrderLineStatus.PROMOTION),
                                new OrderLine("초코바", 2, 2400, OrderLineStatus.FREEBIE),
                                new OrderLine("초코바", 1, 1200, OrderLineStatus.MEMBERSHIP)),
                        new Invoice(List.of(
                                new OrderedProduct("초코바", 5, 6000),
                                new OrderedProduct("콜라", 6, 6000)),
                                List.of(
                                        new FreebieProduct("초코바", 2),
                                        new FreebieProduct("콜라", 2)), 12000, 11,
                                4400, 360, 7240))
        );
    }
}