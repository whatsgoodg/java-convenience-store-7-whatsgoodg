package store.global.exception;

import store.global.enums.ExceptionMessage;

public class OutOfStockException extends Exception {
    public OutOfStockException() {
        super(ExceptionMessage.OUT_OF_STOCK.getMessage());
    }
}
