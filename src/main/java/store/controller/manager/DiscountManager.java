package store.controller.manager;

import store.order.domain.Order;
import store.order.service.DiscountService;
import store.view.InputView;
import store.view.OutputView;

public class DiscountManager {

    private final DiscountService discountService;
    private final InputView inputView;
    private final OutputView outputView;

    public DiscountManager(DiscountService discountService, InputView inputView, OutputView outputView) {
        this.discountService = discountService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void applyDiscounts(Order order) {
        outputView.printMembershipDiscountPrompt();
        boolean validInput = false;
        while (!validInput) {
            try {
                String membershipInput = inputView.readYesOrNo();
                discountService.applyMembershipDiscount(order, membershipInput);
                validInput = true;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    public int calculateTotalDiscount(Order order) {
        return discountService.calculateTotalDiscount(order);
    }
}
