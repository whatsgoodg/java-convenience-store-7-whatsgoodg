package store.global.enums;

public enum OutputMessage {
    GREETING("안녕하세요 W편의점입니다.\n현재 보유하고 있는 상품입니다.\n"),
    PRODUCT_INFO_IN_STOCK("- %s %s원 %d개 %s"),
    PRODUCT_INFO_NO_STOCK("- %s %s원 재고 없음 %s"),

    INVOICE_HEADER("==============W 편의점================"),
    INVOICE_PRODUCT_HEADER(String.format("%-16s %-5s %-10s", "상품명", "수량", "금액")),
    INVOICE_PRODUCT("%-16s %-5s %-10s"),
    INVOICE_FREEBIE_HEADER("=============증\t\t정==============="),
    INVOICE_FREEBIE("%-16s %-5s"),
    INVOICE_PRICE("%-16s %-5s %10s"),
    INVOICE_DIVIDER("=====================================");

    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
