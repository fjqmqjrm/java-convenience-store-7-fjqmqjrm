package store.product.domain.message;

public enum PromotionErrorMessages {
    EMPTY_NAME("프로모션 이름은 비어 있을 수 없습니다."),
    INVALID_QUANTITIES("구매 및 증정 수량은 1 이상이어야 합니다."),
    NULL_DATES("시작일과 종료일은 null일 수 없습니다."),
    END_DATE_BEFORE_START("종료일은 시작일보다 빠를 수 없습니다.");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private final String message;

    PromotionErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }
}