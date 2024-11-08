package store.product.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.product.domain.Promotion;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PromotionRepositoryTest {
    private PromotionRepository promotionRepository;

    @BeforeEach
    public void setUp() {
        promotionRepository = new PromotionRepository();
    }

    @Test
    public void testInitializePromotions() {
        Map<String, Promotion> allPromotions = promotionRepository.findAll();
        assertFalse(allPromotions.isEmpty());
        assertTrue(allPromotions.containsKey("탄산2+1"));
    }

    @Test
    public void testFindByName() {
        Promotion promotion = promotionRepository.findByName("탄산2+1").orElse(null);
        assertNotNull(promotion);
        assertEquals(2, promotion.getBuyQuantity());
    }
}