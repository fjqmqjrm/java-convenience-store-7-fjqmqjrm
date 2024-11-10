package store.handler;

import store.handler.message.ItemHandlerErrorMessages;
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
            processSingleItem(order, item);
        }
    }

    private void processSingleItem(Order order, String[] item) {
        try {
            String productName = inputParser.extractProductName(item);
            int quantity = inputParser.extractQuantity(item);
            processProduct(order, productName, quantity);
        } catch (IllegalArgumentException e) {
            handleInputError(e);
        }
    }

    private void handleInputError(IllegalArgumentException e) {
        outputView.printErrorMessage(e.getMessage());
        List<String[]> newItems = getInputItems();
        handleItems(new Order(), newItems);
    }

    private List<String[]> getInputItems() {
        String newInput = inputView.readItem();
        return inputParser.parseItems(newInput);
    }

    private void processProduct(Order order, String productName, int quantity) {
        Product product = getProduct(productName);
        validateStock(product, quantity);
        Promotion promotion = promotionService.getActivePromotion(product);

        if (promotion == null) {
            addNonPromotionProduct(order, product, quantity);
            return;
        }

        handlePromotion(order, product, promotion, quantity);
    }

    private Product getProduct(String productName) {
        return productRepository.findByName(productName)
                .orElseThrow(() -> new IllegalArgumentException(ItemHandlerErrorMessages.PRODUCT_NOT_FOUND.getMessage()));
    }

    private void validateStock(Product product, int quantity) {
        int availableStock = product.getRegularQuantity() + product.getPromotionStock();
        if (quantity > availableStock) {
            throw new IllegalArgumentException(ItemHandlerErrorMessages.EXCEEDS_STOCK.getMessage());
        }
    }

    private void handlePromotion(Order order, Product product, Promotion promotion, int quantity) {
        int nonPromotionQuantity = promotionService.calculateNonPromotionQuantity(product, quantity);
        int promotionQuantity = quantity - nonPromotionQuantity;

        if (nonPromotionQuantity > 0) {
            handlePartialPromotion(order, product, promotionQuantity, nonPromotionQuantity);
            return;
        }

        addProductWithPromotion(order, product, promotionQuantity, 0);
    }

    private void handlePartialPromotion(Order order, Product product, int promotionQuantity, int nonPromotionQuantity) {
        outputView.printPromotionStockMessage(product.getName(), nonPromotionQuantity);
        String response = getValidatedYesOrNo();

        if ("N".equalsIgnoreCase(response)) {
            nonPromotionQuantity = 0;
            promotionQuantity += nonPromotionQuantity;
        }

        addProductWithPromotion(order, product, promotionQuantity, nonPromotionQuantity);
    }

    private void addNonPromotionProduct(Order order, Product product, int quantity) {
        orderService.addProductToOrder(order, product.getName(), 0, quantity);
    }

    private void addProductWithPromotion(Order order, Product product, int promotionQuantity, int nonPromotionQuantity) {
        boolean addExtraGift = shouldAddExtraGift(product, promotionQuantity);
        int finalPromotionQuantity = calculateFinalQuantity(promotionQuantity, addExtraGift, product);
        orderService.addProductToOrder(order, product.getName(), finalPromotionQuantity, nonPromotionQuantity);
    }

    private boolean shouldAddExtraGift(Product product, int quantity) {
        Promotion promotion = promotionService.getActivePromotion(product);
        if (promotion != null && promotionService.isEligibleForAdditionalPromotion(product, quantity)) {
            outputView.printPromotionAddition(product.getName());
            return "Y".equalsIgnoreCase(getValidatedYesOrNo());
        }
        return false;
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