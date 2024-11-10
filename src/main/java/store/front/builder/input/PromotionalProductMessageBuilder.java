package store.front.builder.input;

import store.global.dto.response.purchase.PromotionalProductInfo;
import store.global.enums.InputMessage;

public class PromotionalProductMessageBuilder {

    public static String build(PromotionalProductInfo promotionalProductInfo) {
        return String.format(InputMessage.PROMOTION.getMessage(), promotionalProductInfo.name(),
                promotionalProductInfo.quantity());
    }
}
