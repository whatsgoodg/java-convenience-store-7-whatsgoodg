package store.back.controller.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Repeater {
    public void executeNoReturn(Runnable action, Consumer<String> errorHandler) {
        while (true) {
            try {
                action.run();
                break;
            } catch (IllegalArgumentException e) {
                errorHandler.accept(e.getMessage());
            }
        }
    }

    public <T> T executeWithReturn(Supplier<T> supplier, Consumer<String> errorHandler) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                errorHandler.accept(e.getMessage());
            }
        }
    }
}