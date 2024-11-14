package store.global.exception;

import store.global.enums.ExceptionMessage;

public class InvalidPurchaseQuantityException extends IllegalArgumentException {
    public InvalidPurchaseQuantityException() {
        super(ExceptionMessage.QUANTITY_BELOW_ZERO.getMessage());
    }
}
