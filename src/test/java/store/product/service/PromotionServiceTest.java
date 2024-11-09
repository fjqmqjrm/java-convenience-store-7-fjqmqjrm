package store.product.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.product.domain.Promotion;
import store.product.repository.PromotionRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PromotionServiceTest {
    private PromotionService promotionService;

    @BeforeEach
    public void setUp() {
        PromotionRepository promotionRepository = new PromotionRepository();
        promotionService = new PromotionService(promotionRepository);
    }

    @Test
    public void testGetPromotionByName() {
        Optional<Promotion> promotionOpt = promotionService.getPromotionByName("탄산2+1");
        assertTrue(promotionOpt.isPresent());
        Promotion promotion = promotionOpt.get();
        assertEquals("탄산2+1", promotion.getName());
    }

    @Test
    public void testIsPromotionActive() {
        LocalDate date = LocalDate.of(2024, 6, 15);
        boolean isActive = promotionService.isPromotionActive("탄산2+1", date);
        assertTrue(isActive);
    }

    @Test
    public void testCalculateBonusQuantity() {
        int bonus = promotionService.calculateBonusQuantity("탄산2+1", 5);
        assertEquals(2, bonus);
    }
}