package store.global.exception;

import store.global.enums.ExceptionMessage;

public class InvalidProductInputException extends IllegalArgumentException {
    public InvalidProductInputException() {
        super(ExceptionMessage.INVALID_PRODUCT_INPUT.getMessage());
    }
}
