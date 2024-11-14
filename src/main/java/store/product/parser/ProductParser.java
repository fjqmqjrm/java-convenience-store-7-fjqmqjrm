package store.product.parser;

import store.product.domain.Product;
import store.product.parser.message.ProductParserErrorMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductParser {

    public List<Product> parseProducts(List<String> lines) {
        Map<String, Product> productMap = new HashMap<>();
        for (String line : lines) {
            if (!isValidLine(line)) continue;
            Product product = parseProduct(line);
            if (product != null) mergeProduct(productMap, product);
        }
        return new ArrayList<>(productMap.values());
    }

    private boolean isValidLine(String line) {
        return !line.trim().isEmpty() && !line.startsWith("name");
    }

    private Product parseProduct(String line) {
        String[] tokens = line.split(",");
        if (!hasValidTokenLength(tokens)) return null;
        String name = tokens[0];
        int price = parseInteger(tokens[1]);
        int quantity = parseInteger(tokens[2]);
        String promotion = parsePromotion(tokens[3]);
        return new Product(name, price, calculateRegularQuantity(promotion, quantity),
                promotion, calculatePromotionStock(promotion, quantity));
    }

    private boolean hasValidTokenLength(String[] tokens) {
        return tokens.length == 4;
    }

    private String parsePromotion(String token) {
        if ("null".equals(token) || token == null) return "NONE";
        return token;
    }

    private int parseInteger(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ProductParserErrorMessages.INVALID_NUMBER_FORMAT.getMessage(token));
        }
    }

    private int calculatePromotionStock(String promotion, int quantity) {
        if (promotion.equals("NONE")) return 0;
        return quantity;
    }

    private int calculateRegularQuantity(String promotion, int quantity) {
        if (promotion.equals("NONE")) return quantity;
        return 0;
    }

    private void mergeProduct(Map<String, Product> productMap, Product newProduct) {
        String name = newProduct.getName();
        if (isRegularProduct(newProduct)) {
            mergeRegularProduct(productMap, newProduct);
            return;
        }
        mergePromotionProduct(productMap, newProduct);
    }

    private boolean isRegularProduct(Product product) {
        return product.getPromotion().equals("NONE");
    }

    private void mergeRegularProduct(Map<String, Product> productMap, Product newProduct) {
        String name = newProduct.getName();
        Product existingProduct = productMap.get(name);
        if (existingProduct != null) {
            existingProduct.setRegularQuantity(existingProduct.getRegularQuantity() + newProduct.getRegularQuantity());
            return;
        }
        productMap.put(name, newProduct);
    }

    private void mergePromotionProduct(Map<String, Product> productMap, Product newProduct) {
        Product existingProduct = productMap.get(newProduct.getName());
        if (existingProduct == null) { productMap.put(newProduct.getName(), newProduct); return; }
        if (isDifferentPromotion(existingProduct, newProduct))
            throw new IllegalArgumentException(ProductParserErrorMessages.MULTIPLE_PROMOTIONS_NOT_ALLOWED.getMessage(newProduct.getName()));
        existingProduct.setPromotionStock(existingProduct.getPromotionStock() + newProduct.getPromotionStock());
        existingProduct.setPromotion(newProduct.getPromotion());
    }

    private boolean isDifferentPromotion(Product existingProduct, Product newProduct) {
        return !existingProduct.getPromotion().equals("NONE") &&
                !existingProduct.getPromotion().equals(newProduct.getPromotion());
    }
}