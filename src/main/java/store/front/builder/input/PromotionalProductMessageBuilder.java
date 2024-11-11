package store.front.builder.input;

import java.util.List;
import store.global.dto.response.purchase.PromotionalProductInfo;
import store.global.enums.InputMessage;

public class PromotionalProductMessageBuilder {
    private static final String COMMA = ",";

    public static String build(final List<PromotionalProductInfo> promotionalProductInfos) {
        StringBuilder productNames = new StringBuilder();
        StringBuilder quantities = new StringBuilder();
        promotionalProductInfos.forEach(promotionalProductInfo -> {
            productNames.append(promotionalProductInfo.name()).append(COMMA);
            quantities.append(promotionalProductInfo.quantity()).append(COMMA);
        });
        String parsedProductNames = productNames.substring(0, productNames.length() - 1);
        String parsedQuantities = quantities.substring(0, quantities.length() - 1);
        return String.format(InputMessage.PROMOTION.getMessage(), parsedProductNames, parsedQuantities);
    }
}
