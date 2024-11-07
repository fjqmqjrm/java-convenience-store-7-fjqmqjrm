package store.domain;

import store.util.message.StoreInventoryErrorMessages;

import java.util.ArrayList;
import java.util.List;

public class StoreInventory {

    private final List<Product> products;

    public StoreInventory(List<Product> initialProducts) {
        if (initialProducts == null || initialProducts.isEmpty()) {
            throw new IllegalArgumentException(StoreInventoryErrorMessages.INVALID_INITIAL_PRODUCTS.getMessage());
        }
        this.products = new ArrayList<>(initialProducts);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
}
