package store.product.domain;

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
            throw new IllegalArgumentException("[ERROR] 프로모션 이름은 비어 있을 수 없습니다.");
        }
    }

    private void validateQuantities(int buyQuantity, int getQuantity) {
        if (buyQuantity <= 0 || getQuantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 구매 및 증정 수량은 1 이상이어야 합니다.");
        }
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("[ERROR] 시작일과 종료일은 null일 수 없습니다.");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("[ERROR] 종료일은 시작일보다 빠를 수 없습니다.");
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