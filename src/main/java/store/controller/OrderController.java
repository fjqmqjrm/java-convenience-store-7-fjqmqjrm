package store.controller;

import store.order.domain.Order;
import store.order.service.OrderService;
import store.order.service.DiscountService;
import store.order.service.ReceiptService;
import store.view.InputView;
import store.view.OutputView;
import store.handler.ItemHandler;

import java.util.List;

public class OrderController {

    private final OrderService orderService;
    private final ItemHandler itemHandler;
    private final DiscountService discountService;
    private final ReceiptService receiptService;
    private final InputView inputView;
    private final OutputView outputView;

    public OrderController(OrderService orderService, ItemHandler itemHandler, DiscountService discountService,
                           ReceiptService receiptService, InputView inputView, OutputView outputView) {
        this.orderService = orderService;
        this.itemHandler = itemHandler;
        this.discountService = discountService;
        this.receiptService = receiptService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        boolean continueShopping = true;
        while (continueShopping) {
            startShoppingSession();
            continueShopping = promptContinueShopping();
        }
    }

    private void startShoppingSession() {
        try {
            processShopping();
        } catch (Exception e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void processShopping() {
        displayAvailableProducts();
        Order order = orderService.createOrder();
        processItems(order);
        applyDiscountsAndPrintReceipt(order);
    }

    private void displayAvailableProducts() {
        outputView.printWelcomeMessage();
        outputView.printProducts(itemHandler.getAllProducts());
    }

    private void processItems(Order order) {
        boolean validInput = false;
        while (!validInput) {
            try {
                List<String[]> items = getCustomerItems();
                itemHandler.handleItems(order, items);
                validInput = true;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private List<String[]> getCustomerItems() {
        String input = inputView.readItem();
        return itemHandler.getInputParser().parseItems(input);
    }

    private void applyDiscountsAndPrintReceipt(Order order) {
        handleMembershipDiscount(order);
        orderService.completeOrder(order);
        printReceipt(order);
    }

    private void handleMembershipDiscount(Order order) {
        outputView.printMembershipDiscountPrompt();
        String membershipInput = inputView.readYesOrNo();
        discountService.applyMembershipDiscount(order, membershipInput);
    }

    private void printReceipt(Order order) {
        int totalDiscount = discountService.calculateTotalDiscount(order);
        outputView.printReceipt(receiptService.generateReceipt(order, totalDiscount).generateReceipt());
    }

    private boolean promptContinueShopping() {
        System.out.println();
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return "Y".equalsIgnoreCase(inputView.readYesOrNo());
    }
}