package store.product.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void testProductCreationSuccess() {
        Product product = new Product("콜라", 1000, 10, "탄산2+1");
        assertEquals("콜라", product.getName());
        assertEquals(1000, product.getPrice());
        assertEquals(10, product.getQuantity());
        assertEquals("탄산2+1", product.getPromotion());
    }

    @Test
    public void testProductCreationWithoutPromotion() {
        Product product = new Product("사이다", 1000, 8, null);
        assertEquals("사이다", product.getName());
        assertEquals("NONE", product.getPromotion());
    }

    @Test
    public void testInvalidProductName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("", 1000, 10, "탄산2+1");
        });
        assertEquals("[ERROR] 상품명은 비어 있을 수 없습니다.", exception.getMessage());
    }

    @Test
    public void testInvalidPrice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("콜라", -100, 10, "탄산2+1");
        });
        assertEquals("[ERROR] 가격은 1 이상 100,000 이하여야 합니다.", exception.getMessage());
    }

    @Test
    public void testInvalidQuantity() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("콜라", 1000, -5, "탄산2+1");
        });
        assertEquals("[ERROR] 재고는 0 이상이어야 합니다.", exception.getMessage());
    }

    @Test
    public void testReduceQuantitySuccess() {
        Product product = new Product("콜라", 1000, 10, "탄산2+1");
        product.reduceQuantity(3);
        assertEquals(7, product.getQuantity());
    }

    @Test
    public void testReduceQuantityExceedingStock() {
        Product product = new Product("콜라", 1000, 5, "탄산2+1");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            product.reduceQuantity(6);
        });
        assertEquals("[ERROR] 재고 수량을 초과하여 감소시킬 수 없습니다.", exception.getMessage());
    }

    @Test
    public void testIncreaseQuantitySuccess() {
        Product product = new Product("콜라", 1000, 5, "탄산2+1");
        product.increaseQuantity(3);
        assertEquals(8, product.getQuantity());
    }
}