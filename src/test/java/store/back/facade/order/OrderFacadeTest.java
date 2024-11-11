package store.back.facade.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.back.repository.ProductRepository;
import store.global.dto.request.order.OrderProductInfo;
import store.global.dto.request.order.OrderRequestDTO;
import store.global.dto.response.order.FreebieProductInfo;
import store.global.dto.response.order.InvoiceInfo;
import store.global.dto.response.order.OrderResponseDTO;
import store.global.dto.response.order.OrderedProductInfo;

class OrderFacadeTest {

    private final OrderFacade orderFacade = new OrderFacade();

    @AfterEach
    void setUp(){
        ProductRepository.loadAgain();
    }

    @ParameterizedTest
    @MethodSource("orderFacadeParams")
    @DisplayName("Facade를 통한 Order 수행 및 OrderResponseDTO 반환, 객체 비교")
    void 주문_테스트(final OrderRequestDTO orderRequestDTO, final InvoiceInfo invoiceInfo) {
        //given
        //when
        OrderResponseDTO orderResponseDTO = orderFacade.orderProducts(orderRequestDTO);
        //then
        assertThat(orderResponseDTO.invoiceInfo()).usingRecursiveComparison().isEqualTo(invoiceInfo);
    }

    private static Stream<Arguments> orderFacadeParams() {
        return Stream.of(
                Arguments.of(
                        new OrderRequestDTO(
                                List.of(
                                        new OrderProductInfo("콜라", 3)), true),
                        new InvoiceInfo(
                                List.of(
                                        new OrderedProductInfo("콜라", 3, 3000)),
                                List.of(
                                        new FreebieProductInfo("콜라", 1)),
                                3000, 3, 1000, 0, 2000)
                ),
                Arguments.of(
                        new OrderRequestDTO(
                                List.of(
                                        new OrderProductInfo("콜라", 3),
                                        new OrderProductInfo("컵라면", 8)), true),
                        new InvoiceInfo(
                                List.of(
                                        new OrderedProductInfo("콜라", 3, 3000),
                                        new OrderedProductInfo("컵라면", 8, 13600)),
                                List.of(
                                        new FreebieProductInfo("콜라", 1)),
                                16600, 11, 1000, 4080, 11520)
                ),
                Arguments.of(
                        new OrderRequestDTO(
                                List.of(
                                        new OrderProductInfo("오렌지주스", 8),
                                        new OrderProductInfo("물", 9)), true),
                        new InvoiceInfo(
                                List.of(
                                        new OrderedProductInfo("물", 9, 4500),
                                        new OrderedProductInfo("오렌지주스", 8, 14400)),
                                List.of(
                                        new FreebieProductInfo("오렌지주스", 4)),
                                18900, 17, 7200, 1350, 10350)
                )
        );
    }
}