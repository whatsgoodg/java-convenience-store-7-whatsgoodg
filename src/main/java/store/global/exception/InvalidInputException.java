package store.global.exception;

import store.global.enums.ExceptionMessage;

public class InvalidInputException extends IllegalArgumentException {
    public InvalidInputException() {
        super(ExceptionMessage.INVALID_YN_INPUT.getMessage());
    }
}
