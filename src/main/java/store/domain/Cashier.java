package store.domain;

import store.util.message.CashierErrorMessages;

import java.util.HashMap;
import java.util.Map;

public class Cashier {
    private final StoreInventory storeInventory;
    private final Map<Product, Integer> purchasedProducts;
    private int totalPrice;
    private boolean isMembershipDiscountApplied;

    public Cashier(StoreInventory storeInventory) {
        if (storeInventory == null) {
            throw new IllegalArgumentException(CashierErrorMessages.NULL_STORE_INVENTORY.getMessage());
        }
        this.storeInventory = storeInventory;
        this.purchasedProducts = new HashMap<>();
        this.totalPrice = 0;
        this.isMembershipDiscountApplied = false;
    }


}