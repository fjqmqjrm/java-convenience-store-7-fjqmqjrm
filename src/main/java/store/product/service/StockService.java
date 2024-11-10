package store.product.service;

import store.product.domain.Product;

public class StockService {

    public void reduceStock(Product product, int quantity, int freeItems) {
        int totalRequiredQuantity = quantity;
        int promotionStockToUse = Math.min(totalRequiredQuantity, product.getPromotionStock());
        if (promotionStockToUse > 0) {
            product.reducePromotionStock(promotionStockToUse);
        }
        int regularStockToUse = totalRequiredQuantity - promotionStockToUse;
        if (regularStockToUse > 0) {
            if (regularStockToUse > product.getRegularQuantity()) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
            }
            product.reduceQuantity(regularStockToUse);
        }
    }
}