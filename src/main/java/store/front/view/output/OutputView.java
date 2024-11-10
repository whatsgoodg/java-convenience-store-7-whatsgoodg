package store.front.view.output;

import store.front.builder.output.InvoiceInfoMessageBuilder;
import store.front.builder.output.ProductInfoMessageBuilder;
import store.global.dto.response.display.ProductDisplayResponseDTO;
import store.global.dto.response.order.OrderResponseDTO;
import store.global.enums.OutputMessage;

public class OutputView {

    public void printErrorMessage(final String message) {
        System.out.println(message);
        System.out.println();
    }

    public void printAllProductInfos(final ProductDisplayResponseDTO productDisplayResponseDTO) {
        System.out.println(OutputMessage.GREETING.getMessage());
        System.out.println(ProductInfoMessageBuilder.build(productDisplayResponseDTO.productInfos()));
    }

    public void printInvoiceInfo(final OrderResponseDTO orderResponseDTO) {
        System.out.println(InvoiceInfoMessageBuilder.build(orderResponseDTO.invoiceInfo()));
    }
}
