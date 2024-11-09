package store.global.enums;

public enum ExceptionMessage {
    OUT_OF_STOCK("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    QUANTITY_BELOW_ZERO("[ERROR] 구매하려는 상품의 개수는 1개 이상이여야 합니다. 다시 입력해 주세요."),
    PRODUCT_NAME_NOT_FOUND("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
