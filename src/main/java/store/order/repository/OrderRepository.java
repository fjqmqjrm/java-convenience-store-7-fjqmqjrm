package store.order.repository;

import store.order.domain.Order;
import java.util.HashMap;
import java.util.Map;


public class OrderRepository {
    private final Map<String, Order> orders = new HashMap<>();

    public void save(Order order) {
        orders.put(order.getId(), order);
    }

}