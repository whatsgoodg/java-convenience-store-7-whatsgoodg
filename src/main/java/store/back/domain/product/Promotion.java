package store.back.domain.product;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final Integer buy;
    private final Integer get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(final String name, final Integer buy, final Integer get, final LocalDate startDate,
                     final LocalDate endDate) {
        validatePromotionPeriod(startDate, endDate);

        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private static void validatePromotionPeriod(final LocalDate startDate, final LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("할인의 시작일이 종료일보다 더 나중입니다.");
        }
    }

    public String getName() {
        return name;
    }

    public Integer getBuy() {
        return buy;
    }

    public Integer getGet() {
        return get;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
