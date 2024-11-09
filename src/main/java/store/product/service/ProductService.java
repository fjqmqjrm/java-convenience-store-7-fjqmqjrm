package store.product.service;

import store.product.domain.Product;
import store.product.repository.ProductRepository;

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
        Optional<Product> productOpt = productRepository.findByName(name);
        if (!productOpt.isPresent()) {
            throw new IllegalArgumentException("[ERROR] 상품을 찾을 수 없습니다: " + name);
        }
        Product product = productOpt.get();
        int promotionStockToReduce = Math.min(product.getPromotionStock(), quantity);
        int regularStockToReduce = quantity - promotionStockToReduce;

        if (promotionStockToReduce > 0) {
            product.reducePromotionStock(promotionStockToReduce);
        }
        if (regularStockToReduce > 0) {
            product.reduceQuantity(regularStockToReduce);
        }
        productRepository.update(product);
    }


}