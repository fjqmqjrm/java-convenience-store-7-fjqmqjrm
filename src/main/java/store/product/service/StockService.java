package store.product.service;

import store.product.domain.Product;
import store.product.repository.ProductRepository;

public class StockService {

    public void validateStock(Product product, int quantity) {
        int totalAvailable = product.getPromotionStock() + product.getRegularQuantity();
        if (quantity > totalAvailable) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

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