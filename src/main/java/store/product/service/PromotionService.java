package store.product.service;

import store.product.domain.Promotion;
import store.product.repository.PromotionRepository;

import java.time.LocalDate;
import java.util.Optional;

public class PromotionService {
    private PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public Optional<Promotion> getPromotionByName(String name) {
        return promotionRepository.findByName(name);
    }

    public boolean isPromotionActive(String promotionName, LocalDate date) {
        Optional<Promotion> promotionOpt = promotionRepository.findByName(promotionName);
        if (!promotionOpt.isPresent()) {
            return false;
        }
        Promotion promotion = promotionOpt.get();
        return promotion.isActive(date);
    }

    public int calculateBonusQuantity(String promotionName, int purchaseQuantity) {
        Optional<Promotion> promotionOpt = promotionRepository.findByName(promotionName);
        if (!promotionOpt.isPresent()) {
            return 0;
        }
        Promotion promotion = promotionOpt.get();
        int buyQuantity = promotion.getBuyQuantity();
        int getQuantity = promotion.getGetQuantity();
        int bonusTimes = purchaseQuantity / buyQuantity;
        return bonusTimes * getQuantity;
    }


}