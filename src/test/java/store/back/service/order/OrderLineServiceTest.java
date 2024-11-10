package store.back.service.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.back.domain.order.OrderLine;
import store.back.domain.order.OrderLineStatus;
import store.global.dto.request.order.OrderProductInfo;
import store.global.dto.request.order.OrderRequestDTO;

class OrderLineServiceTest {

    private final OrderLineService orderLineService = new OrderLineService();

    @ParameterizedTest
    @MethodSource("orderLineCreateParams")
    @DisplayName("구매하고자 하는 상품에 대해 OrderLine들을 생성하는 테스트")
    void 구매_물품_생성_테스트(OrderRequestDTO orderRequestDTO, List<OrderLine> expectedOrderLines) {
        //given
        //when
        List<OrderLine> orderLines = orderLineService.generateOrderLines(orderRequestDTO);
        //then
        assertThat(orderLines).usingRecursiveFieldByFieldElementComparator().containsAll(expectedOrderLines);
    }

    private static Stream<Arguments> orderLineCreateParams() {
        return Stream.of(
                Arguments.of(
                        new OrderRequestDTO(
                                List.of(
                                        new OrderProductInfo("콜라", 12)), true),
                        List.of(new OrderLine("콜라", 6, OrderLineStatus.PROMOTION),
                                new OrderLine("콜라", 3, OrderLineStatus.FREEBIE),
                                new OrderLine("콜라", 3, OrderLineStatus.MEMBERSHIP))),
                Arguments.of(
                        new OrderRequestDTO(
                                List.of(
                                        new OrderProductInfo("콜라", 12),
                                        new OrderProductInfo("컵라면", 10)), true),
                        List.of(
                                new OrderLine("콜라", 6, OrderLineStatus.PROMOTION),
                                new OrderLine("콜라", 3, OrderLineStatus.FREEBIE),
                                new OrderLine("콜라", 3, OrderLineStatus.MEMBERSHIP),
                                new OrderLine("컵라면", 10, OrderLineStatus.MEMBERSHIP))),
                Arguments.of(
                        new OrderRequestDTO(
                                List.of(
                                        new OrderProductInfo("물", 10),
                                        new OrderProductInfo("초코바", 5)), false),
                        List.of(
                                new OrderLine("물", 10, OrderLineStatus.NONE),
                                new OrderLine("초코바", 2, OrderLineStatus.PROMOTION),
                                new OrderLine("초코바", 2, OrderLineStatus.FREEBIE),
                                new OrderLine("초코바", 1, OrderLineStatus.NONE))),
                Arguments.of(
                        new OrderRequestDTO(
                                List.of(
                                        new OrderProductInfo("감자칩", 5),
                                        new OrderProductInfo("오렌지주스", 10),
                                        new OrderProductInfo("에너지바", 5)), false),
                        List.of(
                                new OrderLine("감자칩", 2, OrderLineStatus.PROMOTION),
                                new OrderLine("감자칩", 2, OrderLineStatus.FREEBIE),
                                new OrderLine("감자칩", 1, OrderLineStatus.NONE),
                                new OrderLine("오렌지주스", 4, OrderLineStatus.PROMOTION),
                                new OrderLine("오렌지주스", 4, OrderLineStatus.FREEBIE),
                                new OrderLine("오렌지주스", 2, OrderLineStatus.NONE),
                                new OrderLine("에너지바", 5, OrderLineStatus.NONE))));
    }
}