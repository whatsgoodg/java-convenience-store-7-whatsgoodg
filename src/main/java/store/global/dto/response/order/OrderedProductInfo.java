package store.global.dto.response.order;

import store.back.domain.invoice.OrderedProduct;

public record OrderedProductInfo(String name, Integer quantity, Integer price) {

    public static OrderedProductInfo from(final OrderedProduct orderedProduct) {
        return new OrderedProductInfo(orderedProduct.getName(), orderedProduct.getQuantity(),
                orderedProduct.getPrice());

    }
}
