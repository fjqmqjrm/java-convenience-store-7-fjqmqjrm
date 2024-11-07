package store.util.message;

public enum ProductErrorMessages {
    INVALID_NAME("상품 이름은 공백일 수 없습니다."),
    INVALID_PRICE("상품 가격은 0보다 크고 100,000 이하의 양의 정수여야 합니다."),
    INVALID_QUANTITY("재고는 0 이상의 정수여야 합니다."),
    INVALID_PROMOTION("상품에는 유효한 프로모션이 설정되어야 합니다."),
    INVALID_REDUCE_QUANTITY("감소할 수량은 0 이상이어야 합니다."),
    INSUFFICIENT_QUANTITY("재고가 부족합니다.");

    private static final String FIX = "[ERROR] ";
    private final String message;

    ProductErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return FIX + message;
    }
}
