package store.front.builder.input;

import java.util.List;
import store.global.dto.response.purchase.PromotionalProductInfo;
import store.global.enums.InputMessage;

public class PromotionalProductMessageBuilder {
    private static final String COMMA = ",";

    public static String build(List<PromotionalProductInfo> promotionalProductInfos) {
        StringBuilder productNameBuilder = new StringBuilder();
        StringBuilder productQuantityBuilder = new StringBuilder();

        promotionalProductInfos.forEach(promotionalProductInfo -> {
            productNameBuilder.append(promotionalProductInfo.name()).append(COMMA);
            productQuantityBuilder.append(promotionalProductInfo.quantity()).append(COMMA);
        });
        String builtProductName = productNameBuilder.substring(0, productNameBuilder.length() - 1);
        String builtProductQuantity = productQuantityBuilder.substring(0, productQuantityBuilder.length() - 1);
        return String.format(InputMessage.PROMOTION.getMessage(), builtProductName, builtProductQuantity);
    }
}
