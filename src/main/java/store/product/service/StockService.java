package store.product.service;

import store.product.domain.Product;

public class StockService {
    public void validateStock(Product product, int quantity) {
        int totalAvailable = product.getPromotionStock() + product.getRegularQuantity();
        if (quantity > totalAvailable) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public void reduceStock(Product product, int quantity, int freeItems) {
        int remainingQuantity = quantity - freeItems;
        if (remainingQuantity > 0) {
            product.reduceQuantity(remainingQuantity);
        }
        if (freeItems > 0) {
            product.reducePromotionStock(freeItems);
        }
    }
}