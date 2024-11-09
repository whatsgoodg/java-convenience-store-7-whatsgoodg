package store.back.service.product;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import store.back.domain.Product;
import store.back.repository.ProductRepository;

public class ProductQueryService {
    private final ProductRepository productRepository = new ProductRepository();

    public Optional<Product> findProductWithPromotion(String name) {
        List<Product> findProducts = productRepository.findByName(name);
        return findProducts.stream()
                .filter(findProduct -> !Objects.equals(findProduct.getPromotion().getName(), "null")).findFirst();
    }

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }
}
