package store.domain;

import java.util.ArrayList;
import java.util.List;

public class StoreInventory {

    private final List<Product> products;

    public StoreInventory(List<Product> initialProducts) {
        this.products = new ArrayList<>(initialProducts);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);  // 외부에 불변성 제공
    }
}
