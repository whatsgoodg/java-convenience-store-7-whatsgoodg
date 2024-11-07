package store.back.repository.loader;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import store.back.domain.Product;
import store.back.domain.Promotion;

public class ProductFileLoader {
    private static final String productFilePath = "src/main/resources/products.md";
    private static final List<Promotion> promotions = PromotionFileLoader.loadPromotions();

    public static List<Product> loadPromotions() {
        List<List<String>> rows = FileRowLoader.loadFileToRows(productFilePath);

        return rows.stream().map(ProductFileLoader::mapRowToProduct).toList();
    }

    private static Product mapRowToProduct(List<String> rows) {
        if (rows.size() != Product.class.getDeclaredFields().length) {
            throw new IllegalArgumentException("Promotion의 필드 개수와 column 개수가 동일하지 않습니다.");
        }
        String name = rows.get(0);
        Integer price = Integer.valueOf(rows.get(1));
        Integer quantity = Integer.valueOf(rows.get(2));
        Promotion promotion = findPromotionByName(rows.get(3));

        return new Product(name, price, quantity, promotion);
    }

    private static Promotion findPromotionByName(String name) {
        Optional<Promotion> findPromotion = promotions.stream()
                .filter(promotion -> Objects.equals(promotion.getName(), name)).findFirst();
        if (findPromotion.isEmpty()) {
            throw new IllegalArgumentException("해당 이름의 promotion이 존재하지 않습니다.");
        }
        return findPromotion.get();
    }
}
