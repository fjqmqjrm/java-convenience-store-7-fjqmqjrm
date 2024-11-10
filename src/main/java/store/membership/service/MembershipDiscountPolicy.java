package store.membership.service;

public enum MembershipDiscountPolicy {
    DISCOUNT_RATE(0.3),
    MAX_DISCOUNT_AMOUNT(8000);

    private final double value;

    MembershipDiscountPolicy(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}