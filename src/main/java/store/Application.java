package store;

import camp.nextstep.edu.missionutils.Console;
import store.controller.OrderController;
import store.factory.OrderControllerFactory;

public class Application {
    public static void main(String[] args) {
        OrderController orderController = OrderControllerFactory.createController();
        try {
            orderController.run();
        } finally {
            Console.close();
        }

    }
}