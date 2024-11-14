package store.product.service;

import store.product.domain.Product;
import store.product.repository.ProductRepository;
import store.product.service.message.ProductServiceErrorMessages;

import java.util.Optional;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public boolean isProductAvailable(String name, int quantity) {
        Optional<Product> productOpt = productRepository.findByName(name);
        if (!productOpt.isPresent()) {
            return false;
        }
        Product product = productOpt.get();
        int totalAvailable = product.getRegularQuantity() + product.getPromotionStock();
        return totalAvailable >= quantity;
    }

    public void reduceProductQuantity(String name, int quantity) {
        Product product = findProductOrThrow(name);
        int promotionStockToReduce = Math.min(product.getPromotionStock(), quantity);
        int regularStockToReduce = quantity - promotionStockToReduce;

        reducePromotionStockQuantity(product, promotionStockToReduce);
        reduceRegularStockQuantity(product, regularStockToReduce);

        productRepository.update(product);
    }

    private Product findProductOrThrow(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException(ProductServiceErrorMessages.PRODUCT_NOT_FOUND.getMessage(name)));
    }

    private void reducePromotionStockQuantity(Product product, int promotionStockToReduce) {
        if (promotionStockToReduce > 0) {
            product.reducePromotionStock(promotionStockToReduce);
        }
    }

    private void reduceRegularStockQuantity(Product product, int regularStockToReduce) {
        if (regularStockToReduce > 0) {
            product.reduceQuantity(regularStockToReduce);
        }
    }


}