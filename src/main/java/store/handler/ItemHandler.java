package store.handler;

import store.order.domain.Order;
import store.order.service.OrderService;
import store.product.domain.Product;
import store.product.domain.Promotion;
import store.product.repository.ProductRepository;
import store.product.service.PromotionService;
import store.util.parser.InputParser;
import store.view.InputView;
import store.view.OutputView;

import java.util.Map;
import java.util.List;

public class ItemHandler {

    private final ProductRepository productRepository;
    private final PromotionService promotionService;
    private final OrderService orderService;
    private final InputParser inputParser;
    private final InputView inputView;
    private final OutputView outputView;

    public ItemHandler(ProductRepository productRepository, PromotionService promotionService, OrderService orderService,
                       InputParser inputParser, InputView inputView, OutputView outputView) {
        this.productRepository = productRepository;
        this.promotionService = promotionService;
        this.orderService = orderService;
        this.inputParser = inputParser;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public Map<String, Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void handleItems(Order order, List<String[]> items) {
        for (String[] item : items) {
            try {
                String productName = inputParser.extractProductName(item);
                int quantity = inputParser.extractQuantity(item);
                processProduct(order, productName, quantity);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
                String newInput = inputView.readItem();
                handleItems(order, inputParser.parseItems(newInput));
            }
        }
    }

    private void processProduct(Order order, String productName, int quantity) {
        Product product = getProduct(productName);
        if (!hasSufficientStock(product, quantity)) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }

        Promotion promotion = promotionService.getActivePromotion(product);
        int nonPromotionQuantity = 0;
        int promotionQuantity = quantity;
        if (promotion == null) {
            orderService.addProductToOrder(order, product.getName(), 0, quantity);
            return;
        }
        if (promotion != null) {
            nonPromotionQuantity = promotionService.calculateNonPromotionQuantity(product, quantity);
            promotionQuantity = quantity - nonPromotionQuantity;

            if (nonPromotionQuantity > 0) {
                outputView.printPromotionStockMessage(product.getName(), nonPromotionQuantity);
                String response = getValidatedYesOrNo();
                if ("N".equalsIgnoreCase(response)) {
                    promotionQuantity = quantity - nonPromotionQuantity;
                    nonPromotionQuantity = 0;
                }
            }
        }
        addProductWithPromotion(order, product, promotionQuantity, nonPromotionQuantity);
    }

    private boolean hasSufficientStock(Product product, int quantity) {
        int availableStock = product.getRegularQuantity() + product.getPromotionStock();
        return quantity <= availableStock;
    }

    private Product getProduct(String productName) {
        return productRepository.findByName(productName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    private boolean shouldAddExtraGift(Product product, int quantity) {
        Promotion promotion = promotionService.getActivePromotion(product);
        if (promotion != null && promotionService.isEligibleForAdditionalPromotion(product, quantity)) {
            outputView.printPromotionAddition(product.getName());
            return "Y".equalsIgnoreCase(getValidatedYesOrNo());
        }
        return false;
    }

    private void addProductWithPromotion(Order order, Product product, int promotionQuantity, int nonPromotionQuantity) {
        boolean addExtraGift = shouldAddExtraGift(product, promotionQuantity);
        int finalPromotionQuantity = calculateFinalQuantity(promotionQuantity, addExtraGift, product);

        orderService.addProductToOrder(order, product.getName(), finalPromotionQuantity, nonPromotionQuantity);
    }



    private int calculateFinalQuantity(int quantity, boolean addExtraGift, Product product) {
        Promotion promotion = promotionService.getActivePromotion(product);
        int additionalFreeItems = 0;
        if (addExtraGift && promotion != null) {
            additionalFreeItems = promotionService.calculateFreeItems(quantity, promotion);
        }
        return quantity + additionalFreeItems;
    }

    private String getValidatedYesOrNo() {
        while (true) {
            try {
                return inputView.readYesOrNo();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    public InputParser getInputParser() {
        return inputParser;
    }
}