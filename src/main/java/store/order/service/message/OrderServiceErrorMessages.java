package store.order.service.message;

public enum OrderServiceErrorMessages {
    STOCK_EXCEEDED("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다."),
    ORDER_NOT_CREATED("주문이 생성되지 않았습니다.");

    private final String message;

    OrderServiceErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}