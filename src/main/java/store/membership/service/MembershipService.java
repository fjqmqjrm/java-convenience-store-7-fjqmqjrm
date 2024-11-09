package store.membership.service;

import store.order.domain.Order;
import store.order.domain.OrderItem;

public class MembershipService {
    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;
    private static final int MAX_DISCOUNT_AMOUNT = 8000;

    public int applyMembershipDiscount(Order order) {
        int PromotionTotal = 0;

        for (OrderItem item : order.getOrderItems()) {
            if (item.getPromotionDiscount() == 0) {
                PromotionTotal += item.calculateTotalPrice();
            }
        }

        int discountAmount = (int) (PromotionTotal * MEMBERSHIP_DISCOUNT_RATE);
        return Math.min(discountAmount, MAX_DISCOUNT_AMOUNT);
    }
}