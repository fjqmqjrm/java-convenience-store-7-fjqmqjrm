package store.util.message;

public enum StoreInventoryErrorMessages {
    INVALID_INITIAL_PRODUCTS("초기 제품 목록은 비어있을 수 없습니다.");

    private static final String FIX = "[ERROR] ";
    private final String message;

    StoreInventoryErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return FIX + message;
    }
}
