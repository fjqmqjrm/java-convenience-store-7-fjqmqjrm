package store.product.parser.message;

public enum ProductParserErrorMessages {
    INVALID_NUMBER_FORMAT("숫자 형식이 올바르지 않습니다"),
    MULTIPLE_PROMOTIONS_NOT_ALLOWED("동일 상품에 여러 프로모션이 적용될 수 없습니다");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private final String message;

    ProductParserErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage(String additionalInfo) {
        return ERROR_PREFIX + message + (additionalInfo != null ? ": " + additionalInfo : "");
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }
}