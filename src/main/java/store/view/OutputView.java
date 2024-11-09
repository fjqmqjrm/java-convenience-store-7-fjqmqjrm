package store.view;

import store.product.domain.Product;

import java.util.Map;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");
    }

    public void printProducts(Map<String, Product> products) {
        for (Product product : products.values()) {
            printProductWithPromotion(product);
            printProductWithoutPromotion(product);
        }
    }

    private void printProductWithPromotion(Product product) {
        if (!"NONE".equals(product.getPromotion())) {
            System.out.printf("- %s %s원 %s %s\n",
                    product.getName(),
                    formatPrice(product.getPrice()),
                    getStockMessage(product.getPromotionStock()),
                    product.getPromotion());
        }
    }

    private void printProductWithoutPromotion(Product product) {
        System.out.printf("- %s %s원 %s\n",
                product.getName(),
                formatPrice(product.getPrice()),
                getStockMessage(product.getRegularQuantity()));
    }

    private String formatPrice(int price) {
        return String.format("%,d", price);
    }

    private String getStockMessage(int stock) {
        if (stock == 0) {
            return "재고 없음";
        }
        return stock + "개";
    }

    public void printReceipt(String receipt) {
        System.out.println(receipt);
    }

    public void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printMembershipDiscountPrompt() {
        System.out.println();
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
    }

    public void printPromotionAddition(String productName) {
        System.out.printf("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n", productName);
    }

    public void printPromotionStockMessage(String productName, int quantity) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n", productName, quantity);
    }
}