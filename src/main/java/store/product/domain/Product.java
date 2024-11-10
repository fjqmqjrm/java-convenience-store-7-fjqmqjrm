package store.product.domain;

import store.product.domain.message.ProductErrorMessages;

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
            throw new IllegalArgumentException(ProductErrorMessages.EMPTY_NAME.getMessage());
        }
    }

    private void validatePrice(int price) {
        if (price <= 0 || price > 100000) {
            throw new IllegalArgumentException(ProductErrorMessages.INVALID_PRICE.getMessage());
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(ProductErrorMessages.INVALID_QUANTITY.getMessage());
        }
    }

    private void validatePromotionStock(int promotionStock) {
        if (promotionStock < 0) {
            throw new IllegalArgumentException(ProductErrorMessages.INVALID_PROMOTION_STOCK.getMessage());
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
            throw new IllegalArgumentException(ProductErrorMessages.REDUCE_QUANTITY_ERROR.getMessage());
        }
        if (amount > regularQuantity) {
            throw new IllegalArgumentException(ProductErrorMessages.EXCEEDS_REGULAR_STOCK.getMessage());
        }
        regularQuantity -= amount;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(ProductErrorMessages.INCREASE_QUANTITY_ERROR.getMessage());
        }
        regularQuantity += amount;
    }

    public void reducePromotionStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(ProductErrorMessages.REDUCE_QUANTITY_ERROR.getMessage());
        }
        if (amount > promotionStock) {
            throw new IllegalArgumentException(ProductErrorMessages.EXCEEDS_PROMOTION_STOCK.getMessage());
        }
        promotionStock -= amount;
    }

}