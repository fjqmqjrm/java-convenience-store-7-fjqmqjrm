package store.controller;

import store.controller.manager.DiscountManager;
import store.controller.manager.ReceiptManager;
import store.controller.manager.ShoppingSessionManager;
import store.order.domain.Order;
import store.order.service.OrderService;
import store.view.InputView;
import store.view.OutputView;

public class OrderController {

    private final ShoppingSessionManager shoppingSessionManager;
    private final DiscountManager discountManager;
    private final ReceiptManager receiptManager;
    private final OrderService orderService;
    private final InputView inputView;
    private final OutputView outputView;

    public OrderController(ShoppingSessionManager shoppingSessionManager, DiscountManager discountManager,
                           ReceiptManager receiptManager, OrderService orderService, InputView inputView, OutputView outputView) {
        this.shoppingSessionManager = shoppingSessionManager;
        this.discountManager = discountManager;
        this.receiptManager = receiptManager;
        this.orderService = orderService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        boolean continueShopping = true;
        while (continueShopping) {
            try {
                Order order = shoppingSessionManager.startSession();
                discountManager.applyDiscounts(order);
                orderService.completeOrder(order);
                int totalDiscount = discountManager.calculateTotalDiscount(order);
                receiptManager.generateAndPrintReceipt(order, totalDiscount);
            } catch (Exception e) {
                outputView.printErrorMessage(e.getMessage());
            }
            continueShopping = promptContinueShopping();
        }
    }

    private boolean promptContinueShopping() {
        outputView.printPromptContinueShoppingMessage();
        while (true) {
            try {
                String response = inputView.readYesOrNo();
                return "Y".equals(response);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}