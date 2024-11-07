package store.back.domain;

import java.time.LocalDate;
import java.util.List;

public class Promotion {
    private final String name;
    private final Integer buy;
    private final Integer get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, Integer buy, Integer get, LocalDate startDate, LocalDate endDate) {
        validatePromotionPeriod(startDate, endDate);

        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private static void validatePromotionPeriod(LocalDate startDate, LocalDate endDate) {
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
