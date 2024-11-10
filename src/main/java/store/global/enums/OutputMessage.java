package store.global.enums;

public enum OutputMessage {
    GREETING("안녕하세요 W편의점입니다.\n현재 보유하고 있는 상품입니다.\n"),
    PRODUCT_INFO_IN_STOCK("- %s %s원 %d개 %s"),
    PRODUCT_INFO_NO_STOCK("- %s %s원 재고 없음 %s");

    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
