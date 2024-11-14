package store.order.service;

import store.order.domain.Order;
import store.order.domain.Receipt;

public class ReceiptService {

    public Receipt generateReceipt(Order order, int totalDiscount) {
        int totalAmount = order.calculateTotalAmount();
        int promotionDiscount = order.calculatePromotionDiscount();
        int membershipDiscount = totalDiscount - promotionDiscount;

        int finalAmount = totalAmount - totalDiscount;

        return new Receipt(order, totalAmount, promotionDiscount, membershipDiscount, finalAmount);
    }
}