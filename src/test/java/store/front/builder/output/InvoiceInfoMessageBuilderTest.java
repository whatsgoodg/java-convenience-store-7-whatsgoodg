package store.front.builder.output;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.global.dto.response.order.FreebieProductInfo;
import store.global.dto.response.order.InvoiceInfo;
import store.global.dto.response.order.OrderedProductInfo;

class InvoiceInfoMessageBuilderTest {

    @ParameterizedTest
    @MethodSource("invoiceMessageBuildParams")
    @DisplayName("영수증 정보를 담은 InvoiceInfo를 통해 출력 메시지 생성 테스트")
    void 영수증_출력_메시지_테스트(InvoiceInfo invoiceInfo, List<String> messages){
        //given
        //when
        String buildMessage = InvoiceInfoMessageBuilder.build(invoiceInfo);
        //then
        assertThat(buildMessage).contains(messages);
    }

    private static Stream<Arguments> invoiceMessageBuildParams(){
        return Stream.of(
                Arguments.of(
                        new InvoiceInfo(
                                List.of(
                                        new OrderedProductInfo("콜라", 3, 3000)),
                                List.of(
                                        new FreebieProductInfo("콜라", 1)),
                                3000, 3, 1000, 0, 2000),
                        List.of("==============W 편의점================",
                                "=============증\t\t정===============",
                                "내실돈",
                                "2,000",
                                "=====================================")
                )
        );
    }
}