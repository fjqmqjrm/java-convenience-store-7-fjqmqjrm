package store.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @DisplayName("상품 생성 성공 테스트")
    @Test
    public void 상품_생성_성공_테스트() {
        Product product = new Product("콜라", 1000, 10, "탄산2+1");
        assertEquals("콜라", product.getName());
        assertEquals(1000, product.getPrice());
        assertEquals(10, product.getQuantity());
        assertEquals("탄산2+1", product.getPromotion());
    }

    @DisplayName("프로모션이 없는 경우 생성 성공 테스트")
    @Test
    public void 프로모션_없는_경우_생성_성공_테스트() {
        Product product = new Product("사이다", 1000, 8, null);
        assertEquals("사이다", product.getName());
        assertEquals("NONE", product.getPromotion());
    }

    @DisplayName("상품명이 비어있는 경우 생성 실패 테스트")
    @Test
    public void 상품명_비어있는_경우_생성_실패_테스트() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("", 1000, 10, "탄산2+1");
        });
        assertEquals("[ERROR] 상품명은 비어 있을 수 없습니다.", exception.getMessage());
    }

    @DisplayName("가격이 음수인 경우 생성 실패 테스트")
    @Test
    public void 가격_음수_생성_실패_테스트() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("콜라", -100, 10, "탄산2+1");
        });
        assertEquals("[ERROR] 가격은 1 이상 100,000 이하여야 합니다.", exception.getMessage());
    }

    @DisplayName("재고가 음수인 경우 생성 실패 테스트")
    @Test
    public void 재고_음수_생성_실패_테스트() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("콜라", 1000, -5, "탄산2+1");
        });
        assertEquals("[ERROR] 재고는 0 이상이어야 합니다.", exception.getMessage());
    }

    @DisplayName("재고 감소 로직 성공 테스트")
    @Test
    public void 재고_감소_성공_테스트() {
        Product product = new Product("콜라", 1000, 10, "탄산2+1");
        product.reduceQuantity(3);
        assertEquals(7, product.getQuantity());
    }

    @DisplayName("재고를 초과한 감소 실패 테스트")
    @Test
    public void 재고_초과_감소_실패_테스트() {
        Product product = new Product("콜라", 1000, 5, "탄산2+1");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            product.reduceQuantity(6);
        });
        assertEquals("[ERROR] 재고 수량을 초과하여 감소시킬 수 없습니다.", exception.getMessage());
    }

    @DisplayName("재고 정상 증가 테스트")
    @Test
    public void 재고_정상_증가_테스트() {
        Product product = new Product("콜라", 1000, 5, "탄산2+1");
        product.increaseQuantity(3);
        assertEquals(8, product.getQuantity());
    }
}