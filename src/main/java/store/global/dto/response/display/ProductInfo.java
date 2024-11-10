package store.global.dto.response.display;

import store.back.domain.product.Product;

public record ProductInfo(String name, Integer price, Integer quantity, String promotionName) {

    public static ProductInfo from(Product product) {
        return new ProductInfo(
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getPromotion().getName());
    }
}
