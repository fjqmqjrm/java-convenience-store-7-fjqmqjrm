package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.util.message.CashierErrorMessages;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CashierTest {

    @Test
    @DisplayName("StoreInventory가 null일 때 예외 발생")
    void StoreInventory_null_예외() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Cashier(null));
        assertEquals(CashierErrorMessages.NULL_STORE_INVENTORY.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("유효한 StoreInventory로 Cashier 생성")
    void 유효한_StoreInventory로_Cashier_생성() {
        StoreInventory storeInventory = new StoreInventory(List.of(new Product("콜라", 1000, 10, Promotion.TWO_PLUS_ONE)));
        Cashier cashier = new Cashier(storeInventory);
        assertNotNull(cashier);
    }
}