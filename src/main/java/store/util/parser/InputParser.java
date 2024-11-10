package store.util.parser;

import java.util.ArrayList;
import java.util.List;

public class InputParser {

    public List<String[]> parseItems(String input) {
        validateFormat(input.trim());
        String[] items = input.trim().substring(1, input.length() - 1).split("],\\s*\\[");
        List<String[]> parsedItems = new ArrayList<>();

        for (String item : items) {
            String[] parts = splitAndValidateItem(item.trim());
            parsedItems.add(parts);
        }
        return parsedItems;
    }

    private void validateFormat(String input) {
        if (!input.startsWith("[") || !input.endsWith("]")) {
            throw new IllegalArgumentException("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private String[] splitAndValidateItem(String item) {
        String[] parts = item.replace("[", "").replace("]", "").split("\\s*-\\s*");
        validateItemParts(parts);
        return parts;
    }

    private void validateItemParts(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
        if (parts[0].trim().isEmpty()) {
            throw new IllegalArgumentException("상품명이 비어있습니다. 다시 입력해 주세요.");
        }
        if (!isValidQuantity(parts[1].trim())) {
            throw new IllegalArgumentException("수량은 1 이상의 숫자여야 합니다. 다시 입력해 주세요.");
        }
    }

    private boolean isValidQuantity(String quantityStr) {
        try {
            int quantity = Integer.parseInt(quantityStr);
            return quantity >= 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String extractProductName(String[] parts) {
        return parts[0].trim();
    }

    public int extractQuantity(String[] parts) {
        return Integer.parseInt(parts[1].trim());
    }
}