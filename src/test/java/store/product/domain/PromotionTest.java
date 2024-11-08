package store.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class PromotionTest {

    @DisplayName("프로모션 정상 생성 테스트")
    @Test
    public void 프로모션_정상_생성_테스트() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        Promotion promotion = new Promotion("탄산2+1", 2, 1, startDate, endDate);
        assertEquals("탄산2+1", promotion.getName());
        assertEquals(2, promotion.getBuyQuantity());
        assertEquals(1, promotion.getGetQuantity());
        assertEquals(startDate, promotion.getStartDate());
        assertEquals(endDate, promotion.getEndDate());
    }

    @DisplayName("빈 프로모션명 생성 실패 테스트")
    @Test
    public void 빈_프로모션명_생성_실패_테스트() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(10);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Promotion("", 2, 1, startDate, endDate);
        });
        assertEquals("[ERROR] 프로모션 이름은 비어 있을 수 없습니다.", exception.getMessage());
    }

    @DisplayName("프로모션 구매수량 0 실패 테스트")
    @Test
    public void 프로모션_구매_수량_0_실패_테스트() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(10);

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Promotion("탄산2+1", 0, 1, startDate, endDate);
        });
        assertEquals("[ERROR] 구매 및 증정 수량은 1 이상이어야 합니다.", exception1.getMessage());
    }

    @DisplayName("시작일 종료일 모순 실패 테스트")
    @Test
    public void 시작일_종료일_모순_실패_테스트() {
        LocalDate startDate = LocalDate.now().plusDays(10);
        LocalDate endDate = LocalDate.now();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Promotion("탄산2+1", 2, 1, startDate, endDate);
        });
        assertEquals("[ERROR] 종료일은 시작일보다 빠를 수 없습니다.", exception.getMessage());
    }

    @DisplayName("프로모션 적용가능여부 테스트")
    @Test
    public void 프로모션_적용가능_여부_테스트() {
        LocalDate startDate = LocalDate.now().minusDays(5);
        LocalDate endDate = LocalDate.now().plusDays(5);
        Promotion promotion = new Promotion("탄산2+1", 2, 1, startDate, endDate);

        assertTrue(promotion.isActive(LocalDate.now()));
        assertFalse(promotion.isActive(startDate.minusDays(1)));
        assertFalse(promotion.isActive(endDate.plusDays(1)));
    }
}