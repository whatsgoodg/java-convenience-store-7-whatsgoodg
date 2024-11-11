package store.back.repository.loader;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import store.back.domain.product.Product;
import store.back.domain.product.Promotion;

public class ProductFileLoader {
    private static final String productFilePath = "products.md";
    private static final List<Promotion> promotions = PromotionFileLoader.loadPromotions();

    public static Map<Integer, Product> loadProducts() {
        List<List<String>> rows = FileRowLoader.loadFileToRows(productFilePath);

        List<Product> products = IntStream.range(0, rows.size()).mapToObj(i -> mapRowToProduct(rows.get(i), i + 1))
                .toList();
        return products.stream().collect(Collectors.toMap(Product::getId, product -> product));
    }

    private static Product mapRowToProduct(final List<String> rows, Integer id) {
        if (rows.size() + 1 != Product.class.getDeclaredFields().length) {
            throw new IllegalArgumentException("Promotion의 필드 개수와 column 개수가 동일하지 않습니다.");
        }
        String name = rows.get(0);
        Integer price = Integer.valueOf(rows.get(1));
        Integer quantity = Integer.valueOf(rows.get(2));
        Promotion promotion = findPromotionByName(rows.get(3));

        return new Product(id, name, price, quantity, promotion);
    }

    private static Promotion findPromotionByName(final String name) {
        Optional<Promotion> findPromotion = promotions.stream()
                .filter(promotion -> Objects.equals(promotion.getName(), name)).findFirst();
        if (findPromotion.isEmpty()) {
            throw new IllegalArgumentException("해당 이름의 promotion이 존재하지 않습니다.");
        }
        return findPromotion.get();
    }
}
