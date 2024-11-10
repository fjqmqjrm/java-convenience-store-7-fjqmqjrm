package store.product.service;

import camp.nextstep.edu.missionutils.DateTimes;
import store.product.domain.Product;
import store.product.domain.Promotion;
import store.product.repository.PromotionRepository;

import java.util.Optional;

public class PromotionService {
    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public Optional<Promotion> getPromotionByName(String name) {
        return promotionRepository.findByName(name);
    }

    public boolean isPromotionActive(String promotionName) {
        var currentDate = DateTimes.now().toLocalDate();
        return promotionRepository.findActivePromotionByName(promotionName)
                .filter(promotion -> promotion.isActive(currentDate))
                .isPresent();
    }

    public Promotion getActivePromotion(Product product) {
        String promotionName = product.getPromotion();
        var currentDate = DateTimes.now().toLocalDate();
        return promotionRepository.findActivePromotionByName(promotionName)
                .filter(promotion -> promotion.isActive(currentDate))
                .orElse(null);
    }

    public int calculateFreeItems(int purchaseQuantity, Promotion promotion) {
        var currentDate = DateTimes.now().toLocalDate();
        if (!promotion.isActive(currentDate)) { return 0; }
        int buyQuantity = promotion.getBuyQuantity();
        int getQuantity = promotion.getGetQuantity();
        int setsEligibleForFreeItem = purchaseQuantity / (buyQuantity + getQuantity);
        int freeItems = setsEligibleForFreeItem * getQuantity;
        int remainingQuantity = purchaseQuantity % (buyQuantity + getQuantity);
        if (remainingQuantity >= buyQuantity) { freeItems += getQuantity; }
        return freeItems;
    }

    public boolean isEligibleForAdditionalPromotion(Product product, int quantity) {
        Promotion promotion = getActivePromotion(product);
        if (promotion == null) { return false; }
        int buyQuantity = promotion.getBuyQuantity();
        int promotionSetQuantity = buyQuantity + promotion.getGetQuantity();
        int remainingQuantity = quantity - buyQuantity;
        return remainingQuantity % promotionSetQuantity == 0;
    }

    public int calculateNonPromotionQuantity(Product product, int quantity) {
        Promotion promotion = getActivePromotion(product);
        if (promotion == null) { return quantity; }
        int promotionSetQuantity = promotion.getBuyQuantity() + promotion.getGetQuantity();
        int maxPromotionQuantity = product.getPromotionStock() / promotionSetQuantity * promotionSetQuantity;
        if (quantity > maxPromotionQuantity) { return quantity - maxPromotionQuantity; }
        return 0;
    }
}