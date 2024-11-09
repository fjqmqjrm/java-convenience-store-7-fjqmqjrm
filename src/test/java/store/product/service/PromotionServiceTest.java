package store.product.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.product.domain.Promotion;
import store.product.repository.PromotionRepository;

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



}