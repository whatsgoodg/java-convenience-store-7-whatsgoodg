package store.front.builder.output;

import java.util.List;
import store.global.dto.response.order.FreebieProductInfo;
import store.global.dto.response.order.InvoiceInfo;
import store.global.dto.response.order.OrderedProductInfo;
import store.global.enums.OutputMessage;

public class InvoiceInfoMessageBuilder {
    private static final String NEW_LINE = "\n";

    public static String build(final InvoiceInfo invoiceInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(OutputMessage.INVOICE_HEADER.getMessage()).append(NEW_LINE)
                .append(OutputMessage.INVOICE_PRODUCT_HEADER.getMessage()).append(NEW_LINE);
        String formattedOrderedProduct = formatOrderedProduct(invoiceInfo.orderedProductInfos());
        stringBuilder.append(formattedOrderedProduct).append(OutputMessage.INVOICE_FREEBIE_HEADER.getMessage())
                .append(NEW_LINE);
        String formattedFreebie = formatFreebie(invoiceInfo.freebieProductInfos());
        stringBuilder.append(formattedFreebie).append(OutputMessage.INVOICE_DIVIDER.getMessage()).append(NEW_LINE);
        stringBuilder.append(formatPriceInfos(invoiceInfo)).append(NEW_LINE);
        return stringBuilder.toString();
    }

    private static String formatOrderedProduct(final List<OrderedProductInfo> orderedProductInfoList) {
        StringBuilder stringBuilder = new StringBuilder();
        orderedProductInfoList.forEach(orderedProductInfo -> {
            String format = String.format(OutputMessage.INVOICE_PRODUCT.getMessage(), orderedProductInfo.name(),
                    orderedProductInfo.quantity(), formatNumber(orderedProductInfo.price()));
            stringBuilder.append(format).append(NEW_LINE);
        });
        return stringBuilder.toString();
    }

    private static String formatFreebie(final List<FreebieProductInfo> freebieProductInfos) {
        StringBuilder stringBuilder = new StringBuilder();
        freebieProductInfos.forEach(freebieProductInfo -> {
            String format = String.format(OutputMessage.INVOICE_FREEBIE.getMessage(), freebieProductInfo.name(),
                    freebieProductInfo.quantity());
            stringBuilder.append(format).append(NEW_LINE);
        });
        return stringBuilder.toString();
    }

    private static String formatPriceInfos(final InvoiceInfo invoiceInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        String format = String.format(OutputMessage.INVOICE_PRICE.getMessage(), "총구매액",
                formatNumber(invoiceInfo.totalQuantity()), formatNumber(invoiceInfo.totalPrice()));
        stringBuilder.append(format).append(NEW_LINE);
        stringBuilder.append(formatDisCountInfo("행사할인", invoiceInfo.freebieAmount())).append(NEW_LINE);
        stringBuilder.append(formatDisCountInfo("멤버십할인", invoiceInfo.membershipAmount())).append(NEW_LINE);
        stringBuilder.append(formatPriceToPay(invoiceInfo.priceToPay())).append(NEW_LINE);

        return stringBuilder.toString();
    }

    private static String formatPriceToPay(final Integer priceInfo) {
        String price = formatNumber(priceInfo);
        return String.format(OutputMessage.INVOICE_PRICE.getMessage(), "내실돈", "", price);
    }

    private static String formatDisCountInfo(final String name, final Integer priceInfo) {
        String price = formatNumber(priceInfo);
        return String.format(OutputMessage.INVOICE_PRICE.getMessage(), name, "", "-" + price);
    }

    private static String formatNumber(final Integer number) {
        return String.format("%,d", number);
    }
}
