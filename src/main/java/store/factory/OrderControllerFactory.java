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
        ProductRepository productRepository = new ProductRepository();
        PromotionRepository promotionRepository = new PromotionRepository();
        OrderRepository orderRepository = new OrderRepository();
        PromotionService promotionService = new PromotionService(promotionRepository);
        MembershipService membershipService = new MembershipService();
        StockService stockService = new StockService();
        OrderService orderService = new OrderService(productRepository, orderRepository, membershipService, promotionService, stockService);
        DiscountService discountService = new DiscountService(membershipService);
        ReceiptService receiptService = new ReceiptService();
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        InputParser inputParser = new InputParser();

        ItemHandler itemHandler = new ItemHandler(productRepository, promotionService, orderService, inputParser, inputView, outputView);

        ShoppingSessionManager shoppingSessionManager = new ShoppingSessionManager(itemHandler, inputView, outputView, orderService);
        DiscountManager discountManager = new DiscountManager(discountService, inputView, outputView);
        ReceiptManager receiptManager = new ReceiptManager(receiptService, outputView);

        return new OrderController(shoppingSessionManager, discountManager, receiptManager, orderService, inputView, outputView);
    }
}