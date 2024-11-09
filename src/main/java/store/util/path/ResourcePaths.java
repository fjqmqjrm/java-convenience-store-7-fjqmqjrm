package store.util.path;

public enum ResourcePaths {
    PRODUCT_FILE_PATH("src/main/resources/products.md"),
    PROMOTION_FILE_PATH("src/main/resources/promotions.md");

    private final String path;

    ResourcePaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}