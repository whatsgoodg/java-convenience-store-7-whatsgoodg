package store.front.builder.input;

import java.util.List;
import store.global.dto.response.purchase.NonPromotionalProductInfo;
import store.global.enums.InputMessage;

public class NonPromotionalProductMessageBuilder {
    private static final String COMMA = ",";

    public static String build(List<NonPromotionalProductInfo> nonPromotionalProductInfos) {
        StringBuilder productNameBuilder = new StringBuilder();
        StringBuilder productQuantityBuilder = new StringBuilder();

        nonPromotionalProductInfos.forEach(fullPriceProductInfo -> {
            productNameBuilder.append(fullPriceProductInfo.name()).append(COMMA);
            productQuantityBuilder.append(fullPriceProductInfo.quantity()).append(COMMA);
        });
        String builtProductName = productNameBuilder.substring(0, productNameBuilder.length() - 1);
        String builtProductQuantity = productQuantityBuilder.substring(0, productQuantityBuilder.length() - 1);
        return String.format(InputMessage.NON_PROMOTION.getMessage(), builtProductName, builtProductQuantity);
    }

}
