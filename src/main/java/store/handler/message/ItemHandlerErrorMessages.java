package store.handler.message;

public enum ItemHandlerErrorMessages {
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다."),
    EXCEEDS_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");

    private final String message;

    ItemHandlerErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
