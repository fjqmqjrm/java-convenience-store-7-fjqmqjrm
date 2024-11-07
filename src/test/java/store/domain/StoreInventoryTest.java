package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.util.message.StoreInventoryErrorMessages;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StoreInventoryTest {

    @Test
    @DisplayName("유효한 초기 제품 목록으로 StoreInventory 생성")
    void 유효한_초기_제품_목록으로_StoreInventory_생성() {
        Product product1 = new Product("콜라", 1000, 10, Promotion.TWO_PLUS_ONE);
        Product product2 = new Product("사이다", 1200, 5, Promotion.MD_RECOMMENDED);
        Product product3 = new Product("오렌지주스", 1800, 8, Promotion.NONE);

        StoreInventory storeInventory = new StoreInventory(List.of(product1, product2, product3));

        assertEquals(3, storeInventory.getProducts().size());
        assertEquals(product1, storeInventory.getProducts().get(0));
        assertEquals(product2, storeInventory.getProducts().get(1));
        assertEquals(product3, storeInventory.getProducts().get(2));
    }

    @Test
    @DisplayName("초기 제품 목록이 null인 경우 예외 발생")
    void 초기_제품_목록이_null인_경우_예외_발생() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new StoreInventory(null));
        assertEquals(StoreInventoryErrorMessages.INVALID_INITIAL_PRODUCTS.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("초기 제품 목록이 비어있는 경우 예외 발생")
    void 초기_제품_목록이_비어있는_경우_예외_발생() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new StoreInventory(Collections.emptyList()));
        assertEquals(StoreInventoryErrorMessages.INVALID_INITIAL_PRODUCTS.getMessage(), exception.getMessage());
    }


}
