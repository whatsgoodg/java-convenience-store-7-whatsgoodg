package store.back.repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import store.back.domain.product.Product;
import store.back.repository.loader.ProductFileLoader;

public class ProductRepository {
    private final Map<Integer, Product> products;

    public ProductRepository() {
        List<Product> products = ProductFileLoader.loadProducts();
        this.products = products.stream().collect(Collectors.toMap(Product::getId, product -> product));
    }

    public List<Product> findAll() {
        return products.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue).toList();
    }

    public List<Product> findByName(String name) {
        List<Product> findProducts = findAll();
        return findProducts.stream().filter(product -> Objects.equals(product.getName(), name)).toList();
    }

    public Product save(Product product) {
        if (!products.containsKey(product.getId())) {
            throw new IllegalArgumentException("해당 키를 가진 객체가 존재하지 않습니다.");
        }
        products.put(product.getId(), product);

        return products.get(product.getId());
    }

    public Optional<Product> findById(Integer id) {
        Product product = products.get(id);
        if (product == null) {
            return Optional.empty();
        }

        return Optional.of(product);
    }
}
