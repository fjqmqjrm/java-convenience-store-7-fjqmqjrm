package store.factory;

import store.controller.OrderController;
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
        PromotionService promotionService = createPromotionService();
        OrderService orderService = createOrderService(productRepository, promotionService);
        DiscountService discountService = createDiscountService();
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        InputParser inputParser = new InputParser();
        ItemHandler itemHandler = createItemHandler(productRepository, promotionService, orderService, inputParser, inputView, outputView);
        return new OrderController(orderService, itemHandler, discountService, new ReceiptService(), inputView, outputView);
    }

    private static ProductRepository createProductRepository() {
        return new ProductRepository();
    }

    private static PromotionService createPromotionService() {
        return new PromotionService(new PromotionRepository());
    }

    private static OrderService createOrderService(ProductRepository productRepository, PromotionService promotionService) {
        return new OrderService(productRepository, new OrderRepository(), new MembershipService(), promotionService, new StockService());
    }

    private static DiscountService createDiscountService() {
        return new DiscountService(new MembershipService());
    }

    private static ItemHandler createItemHandler(ProductRepository productRepository, PromotionService promotionService,
                                                 OrderService orderService, InputParser inputParser,
                                                 InputView inputView, OutputView outputView) {
        return new ItemHandler(productRepository, promotionService, orderService, inputParser, inputView, outputView);
    }
}