package store.back.service.product;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import store.back.domain.product.Product;
import store.back.repository.ProductRepository;

public class ProductQueryService {
    private final ProductRepository productRepository = new ProductRepository();

    public Optional<Product> findProductWithPromotion(String name) {
        List<Product> findProducts = productRepository.findByName(name);
        Optional<Product> product = findProducts.stream()
                .filter(findProduct -> !Objects.equals(findProduct.getPromotion().getName(), "null")).findFirst();
        if (product.isEmpty()) {
            return product;
        }
        if (isTodayInPromotionPeriod(product.get())) {
            return product;
        }
        return Optional.empty();
    }

    private Boolean isTodayInPromotionPeriod(Product product) {
        LocalDate now = DateTimes.now().toLocalDate();
        LocalDate startDate = product.getPromotion().getStartDate();
        LocalDate endDate = product.getPromotion().getEndDate();
        return (now.isEqual(startDate) && now.isBefore(endDate)) || (now.isEqual(endDate) && now.isAfter(startDate))
                || (now.isAfter(startDate) && now.isBefore(endDate));
    }

    public Optional<Product> findProductNoPromotion(String name) {
        List<Product> findProducts = productRepository.findByName(name);
        return findProducts.stream().filter(findProduct -> Objects.equals(findProduct.getPromotion().getName(), "null"))
                .findFirst();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
