package store.order.service;

import store.order.domain.Order;
import store.membership.service.MembershipService;

public class DiscountService {
    private final MembershipService membershipService;

    public DiscountService(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    public void applyMembershipDiscount(Order order, String membershipInput) {
        if ("Y".equalsIgnoreCase(membershipInput)) {
            order.setMembership(true);
        }
    }

    public int calculateTotalDiscount(Order order) {
        int promotionDiscount = order.calculatePromotionDiscount();
        int membershipDiscount = 0;
        if (order.isMembership()) {
            membershipDiscount = membershipService.applyMembershipDiscount(order);
        }
        return promotionDiscount + membershipDiscount;
    }
}