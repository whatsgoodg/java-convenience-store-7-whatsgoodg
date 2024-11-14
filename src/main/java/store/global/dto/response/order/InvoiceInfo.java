package store.global.dto.response.order;

import java.util.List;
import store.back.domain.invoice.Invoice;

public record InvoiceInfo(
        List<OrderedProductInfo> orderedProductInfos,
        List<FreebieProductInfo> freebieProductInfos,
        Integer totalPrice,
        Integer totalQuantity,
        Integer freebieAmount,
        Integer membershipAmount,
        Integer priceToPay) {

    public static InvoiceInfo from(final Invoice invoice) {
        return new InvoiceInfo(
                invoice.getOrderedProducts().stream().map(OrderedProductInfo::from).toList(),
                invoice.getFreebieProducts().stream().map(FreebieProductInfo::from).toList(),
                invoice.getTotalPrice(),
                invoice.getTotalQuantity(),
                invoice.getFreebieAmount(),
                invoice.getMembershipAmount(),
                invoice.getPriceToPay());
    }
}
