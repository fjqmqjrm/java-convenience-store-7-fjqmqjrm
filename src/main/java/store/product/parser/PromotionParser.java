package store.product.parser;

import store.product.domain.Promotion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PromotionParser {

    public List<Promotion> parsePromotions(List<String> lines) {
        List<Promotion> promotions = new ArrayList<>();
        for (String line : lines) {
            parseAndAddPromotion(line, promotions);
        }
        return promotions;
    }

    private void parseAndAddPromotion(String line, List<Promotion> promotions) {
        if (isValidLine(line)) {
            Promotion promotion = parsePromotion(line);
            if (promotion != null) {
                promotions.add(promotion);
            }
        }
    }

    private boolean isValidLine(String line) {
        return !line.trim().isEmpty() && !line.startsWith("name");
    }

    private Promotion parsePromotion(String line) {
        String[] tokens = line.split(",");
        if (tokens.length != 5) return null;
        String name = tokens[0];
        int buy = Integer.parseInt(tokens[1]);
        int get = Integer.parseInt(tokens[2]);
        LocalDate startDate = LocalDate.parse(tokens[3]);
        LocalDate endDate = LocalDate.parse(tokens[4]);
        return new Promotion(name, buy, get, startDate, endDate);
    }
}