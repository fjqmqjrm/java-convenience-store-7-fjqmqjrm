package store.order.service;

import store.membership.service.MembershipService;
import store.order.domain.Order;
import store.order.domain.Receipt;
import store.product.domain.Product;
import store.product.domain.Promotion;
import store.order.repository.OrderRepository;
import store.product.repository.ProductRepository;
import store.product.repository.PromotionRepository;

import java.time.LocalDate;
import java.util.Optional;

public class OrderService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final OrderRepository orderRepository;
    private final MembershipService membershipService;

    public OrderService(ProductRepository productRepository,
                        PromotionRepository promotionRepository,
                        OrderRepository orderRepository,
                        MembershipService membershipService) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
        this.orderRepository = orderRepository;
        this.membershipService = membershipService;
    }

    public Order createOrder() {
        Order order = new Order();
        orderRepository.save(order);
        return order;
    }

    public void addProductToOrder(Order order, String productName, int quantity) {
        validateOrder(order);
        Product product = getProduct(productName);
        validateStock(product, quantity);
        Promotion promotion = getActivePromotion(product);
        applyPromotion(order, product, quantity, promotion);
        orderRepository.save(order);
    }

    private void validateOrder(Order order) {
        if (order == null) {
            throw new IllegalStateException("[ERROR] 주문이 생성되지 않았습니다.");
        }
    }

    private Product getProduct(String productName) {
        Optional<Product> optionalProduct = productRepository.findByName(productName);
        if (!optionalProduct.isPresent()) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다.");
        }
        return optionalProduct.get();
    }

    private void validateStock(Product product, int quantity) {
        int totalAvailable = product.getPromotionStock() + product.getRegularQuantity();
        if (quantity > totalAvailable) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
        }
    }

    private Promotion getActivePromotion(Product product) {
        String promotionName = product.getPromotion();
        if ("NONE".equals(promotionName)) {
            return null;
        }

        Optional<Promotion> optionalPromotion = promotionRepository.findByName(promotionName);
        if (!optionalPromotion.isPresent()) {
            return null;
        }

        Promotion promotion = optionalPromotion.get();
        if (promotion.isActive(LocalDate.now())) {
            return promotion;
        }

        return null;
    }

    private int calculateFreeItems(int quantity, Promotion promotion) {
        int buyQuantity = promotion.getBuyQuantity();
        int getQuantity = promotion.getGetQuantity();

        if (quantity % (buyQuantity + getQuantity) == buyQuantity) {
            return getQuantity;
        }
        return 0;
    }

    private void applyPromotion(Order order, Product product, int quantity, Promotion promotion) {
        if (promotion == null) {
            addItemWithoutPromotion(order, product, quantity);
            return;
        }
        int freeItems = calculateFreeItems(quantity, promotion);
        int applicableFreeItems = Math.min(freeItems, product.getPromotionStock());
        int promotionQuantity = Math.min(quantity, product.getPromotionStock());
        product.reducePromotionStock(promotionQuantity);
        int remainingQuantity = quantity - promotionQuantity;
        if (remainingQuantity > 0) {
            product.reduceQuantity(remainingQuantity);
        }

        int finalApplicableFreeItems = 0;
        if (applicableFreeItems > 0) {
            finalApplicableFreeItems = applicableFreeItems;
        }

        order.addOrderItem(product, promotionQuantity + remainingQuantity, promotion, finalApplicableFreeItems);
    }

    private void addItemWithoutPromotion(Order order, Product product, int quantity) {
        order.addOrderItem(product, quantity, null, 0);
        product.reduceQuantity(quantity);
    }

    public Receipt generateReceipt(Order order) {
        validateOrder(order);

        int totalAmount = order.calculateTotalAmount();
        int promotionDiscount = order.calculatePromotionDiscount();
        int amountAfterPromotion = totalAmount - promotionDiscount;

        int membershipDiscount = 0;
        if (order.isMembership()) {
            membershipDiscount = membershipService.applyMembershipDiscount(order);
        }
        int finalAmount = amountAfterPromotion - membershipDiscount;

        return new Receipt(order, totalAmount, promotionDiscount, membershipDiscount, finalAmount);
    }

    public void completeOrder(Order order) {
        validateOrder(order);
        order.completeOrder();
        orderRepository.save(order);
    }


    public boolean isEligibleForAdditionalPromotion(Product product, int quantity) {
        Promotion promotion = getActivePromotion(product);
        if (promotion == null) {
            return false;
        }
        int buyQuantity = promotion.getBuyQuantity();
        int promotionSetQuantity = buyQuantity + promotion.getGetQuantity();
        return (quantity - buyQuantity) % promotionSetQuantity == 0;
    }

    public int calculateAdditionalFreeItems(Product product, int quantity) {
        Promotion promotion = getActivePromotion(product);
        if (promotion == null) {
            return 0;
        }
        int buyQuantity = promotion.getBuyQuantity();
        int promotionSetQuantity = buyQuantity + promotion.getGetQuantity();
        if ((quantity - buyQuantity) % promotionSetQuantity == 0) {
            return promotion.getGetQuantity();
        }
        return 0;
    }

}