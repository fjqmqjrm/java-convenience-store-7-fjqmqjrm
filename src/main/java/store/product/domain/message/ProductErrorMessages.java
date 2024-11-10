package store.product.domain.message;

public enum ProductErrorMessages {
    EMPTY_NAME("상품명은 비어 있을 수 없습니다."),
    INVALID_PRICE("가격은 1 이상 100,000 이하여야 합니다."),
    INVALID_QUANTITY("재고는 0 이상이어야 합니다."),
    INVALID_PROMOTION_STOCK("프로모션 재고는 0 이상이어야 합니다."),
    REDUCE_QUANTITY_ERROR("감소할 수량은 1 이상이어야 합니다."),
    EXCEEDS_REGULAR_STOCK("재고 수량을 초과하여 감소시킬 수 없습니다."),
    INCREASE_QUANTITY_ERROR("증가할 수량은 1 이상이어야 합니다."),
    EXCEEDS_PROMOTION_STOCK("프로모션 재고가 부족합니다.");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private final String message;

    ProductErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }
}