package store.global.dto.response.order;

import store.back.domain.invoice.FreebieProduct;

public record FreebieProductInfo(String name, Integer quantity) {
    public static FreebieProductInfo from(FreebieProduct freebieProduct) {
        return new FreebieProductInfo(freebieProduct.getName(), freebieProduct.getQuantity());
    }
}
