package store.back.domain.order;

public enum OrderLineStatus {
    NONE(1.0),
    PROMOTION(1.0),
    FREEBIE(0.0),
    MEMBERSHIP(0.7);

    private final Double discountRate;

    OrderLineStatus(Double discountRate) {
        this.discountRate = discountRate;
    }

    public Double getDiscountRate() {
        return discountRate;
    }
}
