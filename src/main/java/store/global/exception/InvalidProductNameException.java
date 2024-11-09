package store.global.exception;

import store.global.enums.ExceptionMessage;

public class InvalidProductNameException extends IllegalArgumentException{
    public InvalidProductNameException() {
        super(ExceptionMessage.PRODUCT_NAME_NOT_FOUND.getMessage());
    }
}
