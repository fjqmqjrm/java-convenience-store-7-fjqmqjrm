package store.controller.manager;

import store.order.domain.Order;
import store.order.service.ReceiptService;
import store.view.OutputView;

public class ReceiptManager {

    private final ReceiptService receiptService;
    private final OutputView outputView;

    public ReceiptManager(ReceiptService receiptService, OutputView outputView) {
        this.receiptService = receiptService;
        this.outputView = outputView;
    }

    public void generateAndPrintReceipt(Order order, int totalDiscount) {
        String receipt = receiptService.generateReceipt(order, totalDiscount).generateReceipt();
        outputView.printReceipt(receipt);
    }
}