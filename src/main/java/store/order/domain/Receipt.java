package store.order.domain;

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
        appendOrderItems(sb);
        appendPromotionItems(sb);
        appendSummary(sb);
        return sb.toString();
    }

    private void appendHeader(StringBuilder sb) {
        sb.append("\n");
        sb.append("==============W 편의점================\n");
        sb.append("상품명\t\t수량\t금액\n");
    }

    private void appendOrderItems(StringBuilder sb) {
        for (OrderItem item : order.getOrderItems()) {
            int totalQuantity = item.getQuantity();
            int totalPrice = item.getProduct().getPrice() * totalQuantity;

            sb.append(String.format("%-10s\t%d\t%,d\n",
                    item.getProduct().getName(),
                    totalQuantity,
                    totalPrice));
        }
    }

    private void appendPromotionItems(StringBuilder sb) {
        sb.append("=============증\t정===============\n");
        for (OrderItem item : order.getOrderItems()) {
            if (item.getBonusQuantity() > 0) {
                sb.append(String.format("%-10s\t%d\n",
                        item.getProduct().getName(),
                        item.getBonusQuantity()));
            }
        }
    }

    private void appendSummary(StringBuilder sb) {
        sb.append("====================================\n");
        sb.append(String.format("총구매액\t\t%d\t%,d\n", order.calculateTotalQuantity(), totalAmount));
        sb.append(String.format("행사할인\t\t\t-%d\n", promotionDiscount));
        sb.append(String.format("멤버십할인\t\t\t-%d\n", membershipDiscount));
        sb.append(String.format("내실돈\t\t\t%,d\n", netAmount));
    }
}