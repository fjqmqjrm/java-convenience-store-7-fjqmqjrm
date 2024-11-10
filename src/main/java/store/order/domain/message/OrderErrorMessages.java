package store.order.domain.message;

public enum OrderErrorMessages {
    ORDER_ALREADY_COMPLETED("주문이 이미 완료되었습니다.");

    private final String message;
    private final String ERROR_PREFIX = "[ERROR] ";

    OrderErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }
}