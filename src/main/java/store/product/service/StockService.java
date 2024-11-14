package store.product.service;

import store.product.domain.Product;
import store.product.service.message.StockServiceErrorMessages;

public class StockService {

    public void reduceStock(Product product, int quantity, int freeItems) {
        int totalRequiredQuantity = quantity;
        int promotionStockToUse = Math.min(totalRequiredQuantity, product.getPromotionStock());
        if (promotionStockToUse > 0) { product.reducePromotionStock(promotionStockToUse); }
        int regularStockToUse = totalRequiredQuantity - promotionStockToUse;
        if (regularStockToUse > 0) {
            if (regularStockToUse > product.getRegularQuantity()) {
                throw new IllegalArgumentException(StockServiceErrorMessages.INSUFFICIENT_STOCK.getMessage());
            }
            product.reduceQuantity(regularStockToUse);
        }
    }
}