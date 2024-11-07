package store.util.message;

public enum CashierErrorMessages {
    NULL_STORE_INVENTORY("StoreInventory는 null일 수 없습니다.");

    private static final String FIX = "[ERROR] ";
    private final String message;

    CashierErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return FIX + message;
    }
}