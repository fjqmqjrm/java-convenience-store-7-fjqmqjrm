package store.product.service.message;

public enum StockServiceErrorMessages {
    INSUFFICIENT_STOCK("재고 수량을 초과하여 구매할 수 없습니다.");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private final String message;

    StockServiceErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }
}