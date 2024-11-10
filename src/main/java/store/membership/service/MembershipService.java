package store.membership.service;

import store.order.domain.Order;
import store.order.domain.OrderItem;

public class MembershipService {

    public int applyMembershipDiscount(Order order) {
        int promotionTotal = 0;

        for (OrderItem item : order.getOrderItems()) {
            if (item.getPromotionDiscount() == 0) {
                promotionTotal += item.calculateTotalPrice();
            }
        }

        int discountAmount = (int) (promotionTotal * MembershipDiscountPolicy.DISCOUNT_RATE.getValue());
        return Math.min(discountAmount, (int) MembershipDiscountPolicy.MAX_DISCOUNT_AMOUNT.getValue());
    }
}