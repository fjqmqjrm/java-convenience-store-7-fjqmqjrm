package store.product.domain;

import store.product.domain.message.PromotionErrorMessages;

import java.time.LocalDate;

public class Promotion {
    private String name;
    private int buyQuantity;
    private int getQuantity;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(String name, int buyQuantity, int getQuantity, LocalDate startDate, LocalDate endDate) {
        validateName(name);
        validateQuantities(buyQuantity, getQuantity);
        validateDates(startDate, endDate);
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(PromotionErrorMessages.EMPTY_NAME.getMessage());
        }
    }

    private void validateQuantities(int buyQuantity, int getQuantity) {
        if (buyQuantity <= 0 || getQuantity <= 0) {
            throw new IllegalArgumentException(PromotionErrorMessages.INVALID_QUANTITIES.getMessage());
        }
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException(PromotionErrorMessages.NULL_DATES.getMessage());
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(PromotionErrorMessages.END_DATE_BEFORE_START.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getGetQuantity() {
        return getQuantity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isActive(LocalDate date) {
        return (date.isEqual(startDate) || date.isAfter(startDate))
                && (date.isEqual(endDate) || date.isBefore(endDate));
    }
}