package store.util.parser.message;

public enum InputParserErrorMessages {
    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    EMPTY_PRODUCT_NAME("상품명이 비어있습니다. 다시 입력해 주세요."),
    INVALID_QUANTITY("수량은 1 이상의 숫자여야 합니다. 다시 입력해 주세요.");

    private final String message;

    InputParserErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}