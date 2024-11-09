package store.product.repository;

import store.product.domain.Product;
import store.product.parser.ProductParser;
import store.util.path.ResourcePaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ProductRepository {
    private Map<String, Product> products = new HashMap<>();

    public ProductRepository() {
        initializeProducts();
    }

    private void initializeProducts() {
        List<String> lines = readLines(ResourcePaths.PRODUCT_FILE_PATH.getPath());
        List<Product> productList = parseProducts(lines);
        addProductsToMap(productList);
    }

    private List<String> readLines(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 파일 경로거나 파일을 읽을 수 없습니다.");
        }
    }

    private List<Product> parseProducts(List<String> lines) {
        ProductParser parser = new ProductParser();
        return parser.parseProducts(lines);
    }

    private void addProductsToMap(List<Product> productList) {
        for (Product product : productList) {
            products.put(product.getName(), product);
        }
    }

    public Optional<Product> findByName(String name) {
        return Optional.ofNullable(products.get(name));
    }

    public Map<String, Product> findAll() {
        return new HashMap<>(products);
    }

    public void update(Product product) {
        products.put(product.getName(), product);
    }

}