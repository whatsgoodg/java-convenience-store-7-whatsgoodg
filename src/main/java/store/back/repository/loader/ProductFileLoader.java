package store.back.repository.loader;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import store.back.domain.product.Product;
import store.back.domain.product.Promotion;

public class ProductFileLoader {
    private static final String productFilePath = "products.md";
    private static final List<Promotion> promotions = PromotionFileLoader.loadPromotions();

    public static Map<Integer, Product> loadProducts() {
        List<List<String>> rows = FileRowLoader.loadFileToRows(productFilePath);
        List<List<String>> addedRows = addNonExistedProductRows(rows);
        List<Product> loadedProducts = IntStream.range(0, addedRows.size())
                .mapToObj(i -> mapRowToProduct(addedRows.get(i), i + 1)).toList();
        return loadedProducts.stream().collect(Collectors.toMap(Product::getId, product -> product));
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

    private static List<List<String>> addNonExistedProductRows(List<List<String>> rows) {
        Stack<List<String>> stack = new Stack<>();
        stack.add(rows.getFirst());
        IntStream.range(1, rows.size()).forEach(i -> {
            List<String> first = stack.getFirst();
            if (!first.getFirst().equals(rows.get(i).getFirst()) && !first.getLast().equals("null")) {
                stack.addFirst(List.of(first.getFirst(), first.get(1), "0", "null"));
            }
            stack.addFirst(rows.get(i));
        });
        return stack.stream().toList().reversed();
    }
}
