package store.order.domain;

import java.util.HashMap;
import java.util.Map;

public class Receipt {
    private final Order order;
    private final int totalAmount;
    private final int promotionDiscount;
    private final int membershipDiscount;
    private final int netAmount;

    public Receipt(Order order, int totalAmount, int promotionDiscount, int membershipDiscount, int netAmount) {
        this.order = order;
        this.totalAmount = totalAmount;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
        this.netAmount = netAmount;
    }

    public String generateReceipt() {
        StringBuilder sb = new StringBuilder();
        appendHeader(sb);
        appendMergedOrderItems(sb);
        appendPromotionItems(sb);
        appendSummary(sb);
        return sb.toString();
    }

    private void appendHeader(StringBuilder sb) {
        sb.append("\n==============W 편의점================\n");
        sb.append("상품명\t\t수량\t금액\n");
    }

    private void appendMergedOrderItems(StringBuilder sb) {
        Map<String, Integer> quantityMap = new HashMap<>();
        Map<String, Integer> priceMap = new HashMap<>();
        populateItemMaps(quantityMap, priceMap);

        for (String productName : quantityMap.keySet()) {
            appendItemLine(sb, productName, quantityMap.get(productName), priceMap.get(productName));
        }
    }

    private void populateItemMaps(Map<String, Integer> quantityMap, Map<String, Integer> priceMap) {
        for (OrderItem item : order.getOrderItems()) {
            String productName = item.getProduct().getName();
            int totalQuantity = item.getQuantity();
            int totalPrice = item.getProduct().getPrice() * totalQuantity;

            quantityMap.put(productName, quantityMap.getOrDefault(productName, 0) + totalQuantity);
            priceMap.put(productName, priceMap.getOrDefault(productName, 0) + totalPrice);
        }
    }

    private void appendItemLine(StringBuilder sb, String productName, int totalQuantity, int totalPrice) {
        sb.append(String.format("%-10s\t%d\t%,d\n", productName, totalQuantity, totalPrice));
    }

    private void appendPromotionItems(StringBuilder sb) {
        sb.append("=============증\t정===============\n");
        for (OrderItem item : order.getOrderItems()) {
            if (item.getBonusQuantity() > 0) {
                appendBonusLine(sb, item);
            }
        }
    }

    private void appendBonusLine(StringBuilder sb, OrderItem item) {
        sb.append(String.format("%-10s\t%d\n", item.getProduct().getName(), item.getBonusQuantity()));
    }

    private void appendSummary(StringBuilder sb) {
        sb.append("====================================\n");

        appendSummaryLine(sb, "총구매액", order.calculateTotalQuantity(), totalAmount);
        appendDiscountLine(sb, "행사할인", promotionDiscount);
        appendDiscountLine(sb, "멤버십할인", membershipDiscount);

        appendFinalAmountLine(sb, "내실돈", netAmount);
    }

    private void appendSummaryLine(StringBuilder sb, String label, int quantity, int amount) {
        sb.append(String.format("%s\t\t%d\t%,d\n", label, quantity, amount));
    }

    private void appendDiscountLine(StringBuilder sb, String label, int amount) {
        sb.append(String.format("%s\t\t\t-%,d\n", label, amount));
    }

    private void appendFinalAmountLine(StringBuilder sb, String label, int amount) {
        sb.append(String.format("%s\t\t\t%,d\n", label, amount));
    }
}