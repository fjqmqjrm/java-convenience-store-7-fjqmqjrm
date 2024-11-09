package store.util.parser;

import java.util.ArrayList;
import java.util.List;

public class InputParser {

    public List<String[]> parseItems(String input) {
        if (!input.startsWith("[") || !input.endsWith("]")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
        String[] items = input.substring(1, input.length() - 1).split("],\\[");
        List<String[]> parsedItems = new ArrayList<>();

        for (String item : items) {
            String[] parts = splitItem(item);
            validateItemFormat(parts);
            parsedItems.add(parts);
        }
        return parsedItems;
    }

    private String[] splitItem(String item) {
        return item.replace("[", "").replace("]", "").split("-");
    }

    private void validateItemFormat(String[] parts) {
        if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public String extractProductName(String[] parts) {
        return parts[0].trim();
    }

    public int extractQuantity(String[] parts) {
        try {
            int quantity = Integer.parseInt(parts[1].trim());
            if (quantity < 1) {
                throw new IllegalArgumentException("[ERROR] 수량은 1 이상의 숫자여야 합니다. 다시 입력해 주세요.");
            }
            return quantity;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 수량은 숫자여야 합니다. 다시 입력해 주세요.");
        }
    }
}