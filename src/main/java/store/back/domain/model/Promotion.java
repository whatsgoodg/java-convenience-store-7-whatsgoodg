package store.back.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Promotion {
    private final String name;
    private final Integer buy;
    private final Integer get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(String name, Integer buy, Integer get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion from(List<String> fields) {
        validateFieldsCount(fields);

        String name = fields.get(0);
        Integer buy = Integer.valueOf(fields.get(1));
        Integer get = Integer.valueOf(fields.get(2));
        LocalDate startDate = LocalDate.parse(fields.get(3));
        LocalDate endDate = LocalDate.parse(fields.get(4));

        validatePromotionPeriod(startDate, endDate);
        return new Promotion(name, buy, get, startDate, endDate);
    }

    private static void validateFieldsCount(List<String> fields) {
        if (fields.size() != Promotion.class.getDeclaredFields().length) {
            throw new IllegalArgumentException("객체 생성을 위한 List의 크기가 필드의 개수와 다릅니다.");
        }
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
