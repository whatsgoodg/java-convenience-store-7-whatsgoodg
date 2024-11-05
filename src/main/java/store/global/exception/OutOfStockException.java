package store.global.exception;

import store.global.enums.ExceptionMessage;

public class OutOfStockException extends IllegalArgumentException {
    public OutOfStockException() {
        super(ExceptionMessage.OUT_OF_STOCK.getMessage());
    }
}
