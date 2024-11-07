package store.domain;

import store.util.message.ProductErrorMessages;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    private static final int MAX_PRICE = 100000;

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = validateName(name);
        this.price = validatePrice(price);
        this.quantity = validateQuantity(quantity);
        this.promotion = validatePromotion(promotion);
        validate();
    }

    public int reduceQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(ProductErrorMessages.INVALID_REDUCE_QUANTITY.getMessage());
        }
        if (this.quantity - amount < 0) {
            throw new IllegalArgumentException(ProductErrorMessages.INSUFFICIENT_QUANTITY.getMessage());
        }
        this.quantity -= amount;
        return this.quantity;
    }

    private void validate() {
        validateName(name);
        validatePrice(price);
        validateQuantity(quantity);
        validatePromotion(promotion);
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(ProductErrorMessages.INVALID_NAME.getMessage());
        }
        return name;
    }

    private int validatePrice(Integer price) {
        if (price == null || price <= 0 || price > MAX_PRICE) {
            throw new IllegalArgumentException(ProductErrorMessages.INVALID_PRICE.getMessage());
        }
        return price;
    }

    private int validateQuantity(Integer quantity) {
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException(ProductErrorMessages.INVALID_QUANTITY.getMessage());
        }
        return quantity;
    }

    private Promotion validatePromotion(Promotion promotion) {
        if (promotion == null) {
            throw new IllegalArgumentException(ProductErrorMessages.INVALID_PROMOTION.getMessage());
        }
        return promotion;
    }
}