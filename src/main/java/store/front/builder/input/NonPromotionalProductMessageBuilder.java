package store.front.builder.input;

import store.global.dto.response.purchase.NonPromotionalProductInfo;
import store.global.enums.InputMessage;

public class NonPromotionalProductMessageBuilder {

    public static String build(final NonPromotionalProductInfo nonPromotionalProductInfo) {
        return String.format(InputMessage.NON_PROMOTION.getMessage(), nonPromotionalProductInfo.name(),
                nonPromotionalProductInfo.quantity());
    }
}
