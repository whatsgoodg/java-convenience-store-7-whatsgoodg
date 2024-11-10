package store.front.builder.output;

import java.util.List;
import java.util.Objects;
import store.global.dto.response.display.ProductInfo;
import store.global.enums.OutputMessage;

public class ProductInfoMessageBuilder {
    private static final String NEW_LINE = "\n";

    public static String build(List<ProductInfo> productInfos) {
        StringBuilder stringBuilder = new StringBuilder();

        productInfos.forEach(productInfo -> {
            String productInfoMessage = parseOutputMessage(productInfo);
            stringBuilder.append(productInfoMessage).append(NEW_LINE);
        });
        return stringBuilder.toString();
    }

    private static String parseOutputMessage(ProductInfo productInfo) {
        String name = productInfo.name();
        Integer quantity = productInfo.quantity();
        String price = String.format("%,d", productInfo.price());
        String promotion = parsePromotionName(productInfo.promotionName());

        if (productInfo.quantity() == 0) {
            return String.format(OutputMessage.PRODUCT_INFO_NO_STOCK.getMessage(), name, price, promotion);
        }
        return String.format(OutputMessage.PRODUCT_INFO_IN_STOCK.getMessage(), name, price, quantity, promotion);
    }

    private static String parsePromotionName(String promotionName) {
        if (Objects.equals(promotionName, "null")) {
            return "";
        }
        return promotionName;
    }
}
