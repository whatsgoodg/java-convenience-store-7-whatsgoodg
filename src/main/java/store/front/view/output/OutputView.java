package store.front.view.output;

import store.front.builder.output.ProductInfoMessageBuilder;
import store.global.dto.response.display.ProductDisplayResponseDTO;
import store.global.enums.OutputMessage;

public class OutputView {
    
    public void printErrorMessage(String message) {
        System.out.println(message);
        System.out.println();
    }

    public void printAllProductInfos(ProductDisplayResponseDTO productDisplayResponseDTO) {
        System.out.println(OutputMessage.GREETING.getMessage());
        System.out.println(ProductInfoMessageBuilder.build(productDisplayResponseDTO.productInfos()));
    }
}
