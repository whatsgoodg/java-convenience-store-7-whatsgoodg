package store.back.repository.loader;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.back.domain.product.Product;
import store.back.domain.product.Promotion;

class ProductFileLoaderTest {

    @Test
    @DisplayName("products.md 파일을 읽어 List<Product>를 반환하는 테스트")
    void 파일에서_객체생성_테스트() {
        //given
        Promotion promotion1 = new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"),
                LocalDate.parse("2024-12-31"));
        Promotion promotion2 = new Promotion("null", 1, 1, LocalDate.parse("2024-01-01"),
                LocalDate.parse("2024-01-01"));
        Product product1 = new Product(1, "콜라", 1000, 10, promotion1);
        Product product2 = new Product(16, "컵라면", 1700, 10, promotion2);
        //when
        List<Product> products = ProductFileLoader.loadProducts();
        //then
        assertThat(products.size()).isEqualTo(16);
        assertThat(products).usingRecursiveFieldByFieldElementComparator().contains(product1, product2);
    }
}