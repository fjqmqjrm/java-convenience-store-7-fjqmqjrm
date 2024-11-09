package store.order.domain;

import store.product.domain.Product;
import store.product.domain.Promotion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;
    private final List<OrderItem> orderItems;
    private boolean isCompleted;
    private boolean isMembership;

    public Order() {
        this.id = UUID.randomUUID().toString();
        this.orderItems = new ArrayList<>();
        this.isCompleted = false;
        this.isMembership = false;
    }

    public String getId() {
        return id;
    }

    public void addOrderItem(Product product, int quantity, Promotion promotion, int bonusQuantity) {
        OrderItem item = new OrderItem(product, quantity, promotion, bonusQuantity);
        orderItems.add(item);
    }

    public List<OrderItem> getOrderItems() {
        return new ArrayList<>(orderItems);
    }

    public void completeOrder() {
        if (isCompleted) {
            throw new IllegalStateException("[ERROR] 주문이 이미 완료되었습니다.");
        }
        isCompleted = true;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setMembership(boolean isMembership) {
        this.isMembership = isMembership;
    }

    public boolean isMembership() {
        return isMembership;
    }

    public int calculateTotalAmount() {
        int total = 0;
        for (OrderItem item : orderItems) {
            total += item.calculateTotalPrice();
        }
        return total;
    }

    public int calculatePromotionDiscount() {
        int discount = 0;
        for (OrderItem item : orderItems) {
            discount += item.getPromotionDiscount();
        }
        return discount;
    }


    public int calculateTotalQuantity() {
        int total = 0;
        for (OrderItem item : orderItems) {
            total += item.getQuantity();
        }
        return total;
    }
}