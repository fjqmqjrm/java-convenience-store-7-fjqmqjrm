package store.product.domain;

import java.util.Objects;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

    public Product(String name, int price, int quantity, String promotion) {
        validateName(name);
        validatePrice(price);
        validateQuantity(quantity);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public void reduceQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("[ERROR] 감소할 수량은 1 이상이어야 합니다.");
        }
        if (amount > quantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 감소시킬 수 없습니다.");
        }
        quantity -= amount;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("[ERROR] 증가할 수량은 1 이상이어야 합니다.");
        }
        quantity += amount;
    }
}