package store.back.repository.loader;

import java.time.LocalDate;
import java.util.List;
import store.back.domain.product.Promotion;

public class PromotionFileLoader {
    private static final String promotionFilePath = "promotions.md";

    public static List<Promotion> loadPromotions() {
        List<List<String>> rows = FileRowLoader.loadFileToRows(promotionFilePath);

        return rows.stream().map(PromotionFileLoader::mapRowToPromotion).toList();
    }

    private static Promotion mapRowToPromotion(final List<String> rows) {
        if (rows.size() != Promotion.class.getDeclaredFields().length) {
            throw new IllegalArgumentException("Promotion의 필드 개수와 column 개수가 동일하지 않습니다.");
        }
        String name = rows.get(0);
        Integer buy = Integer.valueOf(rows.get(1));
        Integer get = Integer.valueOf(rows.get(2));
        LocalDate startDate = LocalDate.parse(rows.get(3));
        LocalDate endDate = LocalDate.parse(rows.get(4));

        return new Promotion(name, buy, get, startDate, endDate);
    }
}
