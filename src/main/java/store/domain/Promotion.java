package store.domain;

import java.util.HashMap;
import java.util.Map;

public enum Promotion {
    TWO_PLUS_ONE,
    MD_RECOMMENDED,
    MOMENT_DISCOUNT,
    NONE;

    private static final Map<String, Promotion> PROMOTION_MAP = new HashMap<>();

    static {
        PROMOTION_MAP.put("탄산2+1", TWO_PLUS_ONE);
        PROMOTION_MAP.put("MD추천상품", MD_RECOMMENDED);
        PROMOTION_MAP.put("반짝할인", MOMENT_DISCOUNT);
    }

    public static Promotion from(String promotion) {
        return PROMOTION_MAP.getOrDefault(promotion, NONE);
    }
}