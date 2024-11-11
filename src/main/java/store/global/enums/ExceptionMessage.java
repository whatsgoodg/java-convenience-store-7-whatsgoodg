package store.global.enums;

public enum ExceptionMessage {
    OUT_OF_STOCK("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    QUANTITY_BELOW_ZERO("[ERROR] 구매하려는 상품의 개수는 1개 이상이여야 합니다. 다시 입력해 주세요."),
    PRODUCT_NAME_NOT_FOUND("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INVALID_PRODUCT_INPUT("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_YN_INPUT("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),

    PRODUCT_NOT_FOUND_CART("[ERROR] 상품이 장바구니에 존재하지 않습니다."),
    NO_PURCHASE_PRODUCT_FOUND("[ERROR] 구매하고자 하는 상품이 존재하지 않습니다. 다시 구매를 수행합니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
