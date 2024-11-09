package store.product.domain;

import java.util.Objects;

public class Product {
    private String name;
    private int price;
    private int regularQuantity;
    private int promotionStock;
    private String promotion;

    public Product(String name, int price, int regularQuantity, String promotion, int promotionStock) {
        validateName(name);
        validatePrice(price);
        validateQuantity(regularQuantity);
        validatePromotionStock(promotionStock);
        this.name = name;
        this.price = price;
        this.regularQuantity = regularQuantity;
        this.promotionStock = promotionStock;
        this.promotion = Objects.toString(promotion, "NONE");
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 상품명은 비어 있을 수 없습니다.");
        }
    }

    private void validatePrice(int price) {
        if (price <= 0 || price > 100000) {
            throw new IllegalArgumentException("[ERROR] 가격은 1 이상 100,000 이하여야 합니다.");
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("[ERROR] 재고는 0 이상이어야 합니다.");
        }
    }

    private void validatePromotionStock(int promotionStock) {
        if (promotionStock < 0) {
            throw new IllegalArgumentException("[ERROR] 프로모션 재고는 0 이상이어야 합니다.");
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getRegularQuantity() {
        return regularQuantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotionStock(int promotionStock) {
        this.promotionStock = promotionStock;
    }

    public void setRegularQuantity(int regularQuantity) {
        this.regularQuantity = regularQuantity;
    }

    public int getPromotionStock() {
        return promotionStock;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public void reduceQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("[ERROR] 감소할 수량은 1 이상이어야 합니다.");
        }
        if (amount > regularQuantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 감소시킬 수 없습니다.");
        }
        regularQuantity -= amount;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("[ERROR] 증가할 수량은 1 이상이어야 합니다.");
        }
        regularQuantity += amount;
    }

    public void reducePromotionStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("[ERROR] 감소할 수량은 1 이상이어야 합니다.");
        }
        if (amount > promotionStock) {
            throw new IllegalArgumentException("[ERROR] 프로모션 재고가 부족합니다.");
        }
        promotionStock -= amount;
    }

    public int getApplicableFreeItems(int freeItems) {
        return Math.min(freeItems, this.promotionStock);
    }
}