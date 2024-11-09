package store.global.dto.request.purchase;

import store.global.exception.InvalidPurchaseQuantityException;

public record PurchaseProductInfo(String name, Integer quantity) {
    public PurchaseProductInfo(String name, Integer quantity) {
        validate(quantity);
        this.name = name;
        this.quantity = quantity;
    }

    private void validate(Integer quantity) {
        if (quantity < 1) {
            throw new InvalidPurchaseQuantityException();
        }
    }
}
