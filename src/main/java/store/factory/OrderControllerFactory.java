package store.factory;

import store.controller.OrderController;
import store.controller.manager.DiscountManager;
import store.controller.manager.ReceiptManager;
import store.controller.manager.ShoppingSessionManager;
import store.membership.service.MembershipService;
import store.order.repository.OrderRepository;
import store.order.service.OrderService;
import store.order.service.DiscountService;
import store.order.service.ReceiptService;
import store.product.repository.ProductRepository;
import store.product.repository.PromotionRepository;
import store.product.service.PromotionService;
import store.product.service.StockService;
import store.util.parser.InputParser;
import store.view.InputView;
import store.view.OutputView;
import store.handler.ItemHandler;

public class OrderControllerFactory {

    public static OrderController createController() {
        ProductRepository productRepository = createProductRepository();
        OrderRepository orderRepository = createOrderRepository();
        PromotionService promotionService = createPromotionService();
        MembershipService membershipService = new MembershipService();
        StockService stockService = new StockService();
        OrderService orderService = new OrderService(productRepository, orderRepository, membershipService, promotionService, stockService);
        return new OrderController(createShoppingSessionManager(orderService, productRepository, promotionService), createDiscountManager(membershipService),
                createReceiptManager(), orderService, new InputView(), new OutputView()
        );
    }

    private static ProductRepository createProductRepository() {
        return new ProductRepository();
    }

    private static OrderRepository createOrderRepository() {
        return new OrderRepository();
    }

    private static PromotionService createPromotionService() {
        PromotionRepository promotionRepository = new PromotionRepository();
        return new PromotionService(promotionRepository);
    }

    private static ShoppingSessionManager createShoppingSessionManager(OrderService orderService, ProductRepository productRepository, PromotionService promotionService) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        InputParser inputParser = new InputParser();
        ItemHandler itemHandler = new ItemHandler(productRepository, promotionService, orderService, inputParser, inputView, outputView);
        return new ShoppingSessionManager(itemHandler, inputView, outputView, orderService);
    }

    private static DiscountManager createDiscountManager(MembershipService membershipService) {
        DiscountService discountService = new DiscountService(membershipService);
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        return new DiscountManager(discountService, inputView, outputView);
    }

    private static ReceiptManager createReceiptManager() {
        ReceiptService receiptService = new ReceiptService();
        OutputView outputView = new OutputView();
        return new ReceiptManager(receiptService, outputView);
    }
}