package store.util.parser;

import store.util.parser.message.InputParserErrorMessages;

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
            throw new IllegalArgumentException(InputParserErrorMessages.INVALID_FORMAT.getMessage());
        }
    }

    private String[] splitAndValidateItem(String item) {
        String[] parts = item.replace("[", "").replace("]", "").split("\\s*-\\s*");
        validateItemParts(parts);
        return parts;
    }

    private void validateItemParts(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException(InputParserErrorMessages.INVALID_FORMAT.getMessage());
        }
        if (parts[0].trim().isEmpty()) {
            throw new IllegalArgumentException(InputParserErrorMessages.EMPTY_PRODUCT_NAME.getMessage());
        }
        if (!isValidQuantity(parts[1].trim())) {
            throw new IllegalArgumentException(InputParserErrorMessages.INVALID_QUANTITY.getMessage());
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