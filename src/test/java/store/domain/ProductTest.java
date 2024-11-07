package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.util.message.ProductErrorMessages;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("상품 이름이 공백일 때 예외 발생")
    void 상품_이름_공백_예외() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product(" ", 1000, 10, Promotion.TWO_PLUS_ONE));
        assertEquals(ProductErrorMessages.INVALID_NAME.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("상품 이름이 null일 때 예외 발생")
    void 상품_이름_null_예외() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product(null, 1000, 10, Promotion.TWO_PLUS_ONE));
        assertEquals(ProductErrorMessages.INVALID_NAME.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("상품 가격이 음수일 때 예외 발생")
    void 상품_가격_음수_예외() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product("콜라", -100, 10, Promotion.TWO_PLUS_ONE));
        assertEquals(ProductErrorMessages.INVALID_PRICE.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("상품 가격이 최대값을 초과할 때 예외 발생")
    void 상품_가격_최대값_초과_예외() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product("콜라", 100001, 10, Promotion.TWO_PLUS_ONE));
        assertEquals(ProductErrorMessages.INVALID_PRICE.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("재고가 음수일 때 예외 발생")
    void 재고_음수_예외() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product("콜라", 1000, -5, Promotion.TWO_PLUS_ONE));
        assertEquals(ProductErrorMessages.INVALID_QUANTITY.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("프로모션이 null일 때 예외 발생")
    void 프로모션_null_예외() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product("콜라", 1000, 10, null));
        assertEquals(ProductErrorMessages.INVALID_PROMOTION.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("수량 감소가 성공적으로 수행될 때")
    void 수량_감소_성공() {
        Product product = new Product("콜라", 1000, 10, Promotion.TWO_PLUS_ONE);
        int remainingQuantity = product.reduceQuantity(3);
        assertEquals(7, remainingQuantity);
    }

    @Test
    @DisplayName("수량 감소 시 재고 부족 예외 발생")
    void 수량_감소_부족_예외()
    {
        Product product = new Product("콜라", 1000, 5, Promotion.TWO_PLUS_ONE);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> product.reduceQuantity(10));
        assertEquals(ProductErrorMessages.INSUFFICIENT_QUANTITY.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("수량 감소 시 음수 입력 예외 발생")
    void 수량_감소_음수_예외() {
        Product product = new Product("콜라", 1000, 10, Promotion.TWO_PLUS_ONE);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> product.reduceQuantity(-3));
        assertEquals(ProductErrorMessages.INVALID_REDUCE_QUANTITY.getMessage(), exception.getMessage());
    }
}