package store;

import store.controller.OrderController;
import store.order.service.OrderService;
import store.membership.service.MembershipService;
import store.order.repository.OrderRepository;
import store.product.repository.ProductRepository;
import store.product.repository.PromotionRepository;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        PromotionRepository promotionRepository = new PromotionRepository();
        OrderRepository orderRepository = new OrderRepository();
        MembershipService membershipService = new MembershipService();

        OrderService orderService = new OrderService(productRepository, promotionRepository, orderRepository, membershipService);
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        OrderController orderController = new OrderController(orderService, productRepository, inputView, outputView);

        orderController.run();
    }
}