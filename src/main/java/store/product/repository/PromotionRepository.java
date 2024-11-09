package store.product.repository;

import store.product.domain.Promotion;
import store.product.parser.PromotionParser;
import store.util.path.ResourcePaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class PromotionRepository {
    private Map<String, Promotion> promotions = new HashMap<>();

    public PromotionRepository() {
        initializePromotions();
    }

    private void initializePromotions() {
        List<String> lines = readLines(ResourcePaths.PROMOTION_FILE_PATH.getPath());
        List<Promotion> promotionList = parsePromotions(lines);
        addPromotionsToMap(promotionList);
    }

    private List<String> readLines(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 파일 경로거나 파일을 읽을 수 없습니다.");
        }
    }

    private List<Promotion> parsePromotions(List<String> lines) {
        PromotionParser parser = new PromotionParser();
        return parser.parsePromotions(lines);
    }

    private void addPromotionsToMap(List<Promotion> promotionList) {
        for (Promotion promotion : promotionList) {
            promotions.put(promotion.getName(), promotion);
        }
    }

    public Optional<Promotion> findByName(String name) {
        return Optional.ofNullable(promotions.get(name));
    }

    public Map<String, Promotion> findAll() {
        return new HashMap<>(promotions);
    }

    public Optional<Promotion> findActivePromotionByName(String name) {
        return findByName(name).filter(this::isPromotionValid);
    }

    private boolean isPromotionValid(Promotion promotion) {
        LocalDate currentDate = LocalDate.now();
        return !currentDate.isBefore(promotion.getStartDate()) && !currentDate.isAfter(promotion.getEndDate());
    }
}