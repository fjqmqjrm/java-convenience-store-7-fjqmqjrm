package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String readItem() {
        System.out.println();
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }

    public String readYesOrNo() {
        String input = Console.readLine().trim().toUpperCase();
        if (!input.equals("Y") && !input.equals("N")) {
            throw new IllegalArgumentException("올바르지 않은 입력입니다. Y 또는 N을 입력해 주세요.");
        }
        return input;
    }
}