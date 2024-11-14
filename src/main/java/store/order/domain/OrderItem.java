package store.order.domain;

import store.product.domain.Product;
import store.product.domain.Promotion;

public class OrderItem {
    private final Product product;
    private final int quantity;
    private final int bonusQuantity;
    private final int promotionDiscount;

    public OrderItem(Product product, int quantity, Promotion promotion, int bonusQuantity) {
        this.product = product;
        this.quantity = quantity;
        this.bonusQuantity = bonusQuantity;
        this.promotionDiscount = calculatePromotionDiscount(promotion);
    }

    private int calculatePromotionDiscount(Promotion promotion) {
        if (promotion == null) {
            return 0;
        }
        int freeItems = bonusQuantity;

        return freeItems * product.getPrice();
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getBonusQuantity() {
        return bonusQuantity;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int calculateTotalPrice() {
        return quantity * product.getPrice();
    }
}