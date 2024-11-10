package store.order.service;

import store.membership.service.MembershipService;
import store.order.domain.Order;
import store.product.domain.Product;
import store.product.domain.Promotion;
import store.order.repository.OrderRepository;
import store.product.repository.ProductRepository;
import store.product.service.PromotionService;
import store.product.service.StockService;


public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final MembershipService membershipService;
    private final PromotionService promotionService;
    private final StockService stockService;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository, MembershipService membershipService, PromotionService promotionService, StockService stockService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.membershipService = membershipService;
        this.promotionService = promotionService;
        this.stockService = stockService;
    }

    public Order createOrder() {
        Order order = new Order();
        orderRepository.save(order);
        return order;
    }

    public void addProductToOrder(Order order, String productName, int promotionQuantity, int nonPromotionQuantity) {
        Product product = getProduct(productName);
        validateStock(product, promotionQuantity + nonPromotionQuantity);
        Promotion promotion = promotionService.getActivePromotion(product);
        if (promotion != null) {
            applyPromotion(order, product, promotionQuantity, promotion);
        }
        if (nonPromotionQuantity > 0) {
            order.addOrderItem(product, nonPromotionQuantity, null, 0);
            stockService.reduceStock(product, nonPromotionQuantity, 0);
        }
        orderRepository.save(order);
    }


    private void validateStock(Product product, int quantity) {
        int availableStock = product.getPromotionStock() + product.getRegularQuantity();
        if (quantity > availableStock) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    private Product getProduct(String productName) {
        return productRepository.findByName(productName)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다."));
    }


    private void applyPromotion(Order order, Product product, int quantity, Promotion promotion) {
        int freeItems = 0;
        if (promotion != null) {
            freeItems = promotionService.calculateFreeItems(quantity, promotion);
            if (freeItems > quantity) {
                freeItems = quantity;
            }
        }
        order.addOrderItem(product, quantity, promotion, freeItems);
        stockService.reduceStock(product, quantity, freeItems);
    }


    private void validateOrder(Order order) {
        if (order == null) {
            throw new IllegalStateException("[ERROR] 주문이 생성되지 않았습니다.");
        }
    }

    public void completeOrder(Order order) {
        validateOrder(order);
        order.completeOrder();
        orderRepository.save(order);
    }
}