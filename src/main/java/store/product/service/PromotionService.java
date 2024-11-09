package store.product.service;

import store.product.domain.Product;
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

    public Promotion getActivePromotion(Product product) {
        String promotionName = product.getPromotion();
        if ("NONE".equals(promotionName)) {
            return null;
        }
        Optional<Promotion> optionalPromotion = promotionRepository.findByName(promotionName);
        if (!optionalPromotion.isPresent()) {
            return null;
        }
        Promotion promotion = optionalPromotion.get();
        if (promotion.isActive(LocalDate.now())) {
            return promotion;
        }
        return null;
    }

    public int calculateFreeItems(int quantity, Promotion promotion) {
        int buyQuantity = promotion.getBuyQuantity();
        int getQuantity = promotion.getGetQuantity();
        int freeItems = 0;
        if (quantity >= buyQuantity) {
            int applicableSets = quantity / buyQuantity;
            freeItems = applicableSets * getQuantity;
        }
        return freeItems;
    }

    public boolean isEligibleForAdditionalPromotion(Product product, int quantity) {
        Promotion promotion = getActivePromotion(product);
        if (promotion == null) {
            return false;
        }
        int buyQuantity = promotion.getBuyQuantity();
        int promotionSetQuantity = buyQuantity + promotion.getGetQuantity();
        int remainingQuantity = quantity - buyQuantity;
        return remainingQuantity % promotionSetQuantity == 0;
    }

    public int calculateAdditionalFreeItems(Product product, int quantity) {
        Promotion promotion = getActivePromotion(product);
        if (promotion == null) {
            return 0;
        }
        if (isEligibleForAdditionalPromotion(product, quantity)) {
            return promotion.getGetQuantity();
        }
        return 0;
    }
}