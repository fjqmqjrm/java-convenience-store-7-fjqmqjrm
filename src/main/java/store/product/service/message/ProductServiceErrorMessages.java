package store.product.service.message;

public enum ProductServiceErrorMessages {
    PRODUCT_NOT_FOUND("상품을 찾을 수 없습니다");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private final String message;

    ProductServiceErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage(String details) {
        return ERROR_PREFIX + message + ": " + details;
    }
}