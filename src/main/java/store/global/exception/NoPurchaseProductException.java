package store.global.exception;

import store.global.enums.ExceptionMessage;

public class NoPurchaseProductException extends IllegalArgumentException {
    public NoPurchaseProductException() {
        super(ExceptionMessage.NO_PURCHASE_PRODUCT_FOUND.getMessage());
    }
}
