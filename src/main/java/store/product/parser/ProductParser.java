package store.product.parser;

import store.product.domain.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductParser {

    public List<Product> parseProducts(List<String> lines) {
        List<Product> products = new ArrayList<>();
        for (String line : lines) {
            if (isValidLine(line)) {
                Product product = parseProduct(line);
                if (product != null) {
                    products.add(product);
                }
            }
        }
        return products;
    }

    private boolean isValidLine(String line) {
        return !line.trim().isEmpty() && !line.startsWith("name");
    }

    private Product parseProduct(String line) {
        String[] tokens = line.split(",");
        if (tokens.length != 4) {
            return null;
        }
        String name = tokens[0];
        int price = Integer.parseInt(tokens[1]);
        int quantity = Integer.parseInt(tokens[2]);
        String promotion = parsePromotion(tokens[3]);
        return new Product(name, price, quantity, promotion);
    }

    private String parsePromotion(String token) {
        String promotion = token;
        if ("null".equals(promotion)) {
            promotion = null;
        }
        return promotion;
    }
}