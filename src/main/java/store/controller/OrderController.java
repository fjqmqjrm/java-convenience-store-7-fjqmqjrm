package store.controller;

import store.order.domain.Order;
import store.order.domain.Receipt;
import store.order.service.OrderService;
import store.product.domain.Product;
import store.product.repository.ProductRepository;
import store.view.InputView;
import store.view.OutputView;

import java.util.Map;

public class OrderController {

    private final OrderService orderService;
    private final ProductRepository productRepository;
    private final InputView inputView;
    private final OutputView outputView;

    public OrderController(OrderService orderService, ProductRepository productRepository, InputView inputView, OutputView outputView) {
        this.orderService = orderService;
        this.productRepository = productRepository;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        boolean continueShopping = true;

        while (continueShopping) {
            try {
                processShopping();
            } catch (Exception e) {
                outputView.printErrorMessage(e.getMessage());
            }

            continueShopping = promptContinueShopping();
        }
    }

    private void processShopping() {
        defaultStartPrint();
        Order order = orderService.createOrder();
        String[] items = getCustomerItems();

        for (String item : items) {
            handleItem(order, item);
        }

        applyDiscountsAndPrintReceipt(order);
    }

    private void defaultStartPrint() {
        outputView.printWelcomeMessage();
        Map<String, Product> products = productRepository.findAll();
        outputView.printProducts(products);
    }

    private String[] getCustomerItems() {
        String input = inputView.readItem();
        return parseInput(input);
    }

    private void handleItem(Order order, String item) {
        String[] parts = item.split("-");
        validateInput(parts);
        String productName = parts[0].trim();
        int quantity = parseQuantity(parts[1].trim());

        Product product = getProduct(productName);
        quantity = checkAndApplyPromotion(product, quantity);

        orderService.addProductToOrder(order, productName, quantity);
    }

    private Product getProduct(String productName) {
        return productRepository.findByName(productName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    private int checkAndApplyPromotion(Product product, int quantity) {
        if (orderService.isEligibleForAdditionalPromotion(product, quantity)) {
            outputView.printPromotionAddition(product.getName());
            String response = inputView.readYesOrNo();
            if ("Y".equalsIgnoreCase(response)) {
                int additionalFreeItems = orderService.calculateAdditionalFreeItems(product, quantity);
                quantity += additionalFreeItems;
            }
        }
        return quantity;
    }

    private void applyDiscountsAndPrintReceipt(Order order) {
        handleMembershipDiscount(order);
        orderService.completeOrder(order);
        Receipt receipt = orderService.generateReceipt(order);
        outputView.printReceipt(receipt.generateReceipt());
    }

    private String[] parseInput(String input) {
        return input.replaceAll("[\\[\\]]", "").split(",");
    }

    private void validateInput(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private int parseQuantity(String quantityStr) {
        try {
            return Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("수량은 숫자여야 합니다. 다시 입력해 주세요.");
        }
    }

    private void handleMembershipDiscount(Order order) {
        outputView.printMembershipDiscountPrompt();
        String membershipInput = inputView.readYesOrNo();
        if ("Y".equalsIgnoreCase(membershipInput)) {
            order.setMembership(true);
        }
    }

    private boolean promptContinueShopping() {
        System.out.println();
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        String addMore = inputView.readYesOrNo();
        return "Y".equals(addMore);
    }
}