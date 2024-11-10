package store.controller.manager;

import store.handler.ItemHandler;
import store.order.domain.Order;
import store.order.service.OrderService;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;

public class ShoppingSessionManager {

    private final ItemHandler itemHandler;
    private final InputView inputView;
    private final OutputView outputView;
    private final OrderService orderService;

    public ShoppingSessionManager(ItemHandler itemHandler, InputView inputView, OutputView outputView, OrderService orderService) {
        this.itemHandler = itemHandler;
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderService = orderService;
    }

    public Order startSession() {
        outputView.printWelcomeMessage();
        outputView.printProducts(itemHandler.getAllProducts());
        Order order = orderService.createOrder();
        processItems(order);
        return order;
    }

    private void processItems(Order order) {
        boolean validInput = false;
        while (!validInput) {
            try {
                String input = inputView.readItem();
                List<String[]> items = itemHandler.getInputParser().parseItems(input);
                itemHandler.handleItems(order, items);
                validInput = true;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}