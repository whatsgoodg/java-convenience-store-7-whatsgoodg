package store.back.domain.model;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.global.exception.OutOfStockException;

class ProductTest {

    @Test
    @DisplayName("Product 객체 생성 테스트")
    void 객체_생성_테스트() {
        //given
        String name = "asd";
        Integer price = 1000;
        Integer quantity = 1000;
        List<String> fields = List.of("asd", "1", "1", "2024-11-01", "2024-11-01");
        //when
        Product product = new Product(name, price, quantity, Promotion.from(fields));
        Promotion promotion = product.getPromotion();
        //then
        assertThat(product.getName()).isEqualTo("asd");
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getQuantity()).isEqualTo(1000);
        assertThat(product.getQuantity()).isEqualTo(1000);
        // promotion
        assertThat(product.getPromotion().getName()).isEqualTo("asd");
        assertThat(product.getPromotion().getBuy()).isEqualTo(1);
        assertThat(product.getPromotion().getGet()).isEqualTo(1);
        assertThat(product.getPromotion().getStartDate()).isEqualTo(LocalDate.parse("2024-11-01"));
        assertThat(product.getPromotion().getEndDate()).isEqualTo(LocalDate.parse("2024-11-01"));
    }

    @Test
    @DisplayName("Product decreaseQuantity 테스트")
    void 수량_감소_테스트() {
        //given
        String name = "asd";
        Integer price = 1000;
        Integer quantity = 1000;
        List<String> fields = List.of("asd", "1", "1", "2024-11-01", "2024-11-01");
        //when
        Product product = new Product(name, price, quantity, Promotion.from(fields));
        product.decreaseQuantity(1000);
        //then
        assertThat(product.getQuantity()).isEqualTo(0);
    }

    @Test
    @DisplayName("Product decreaseQuantity 예외 테스트")
    void 수량_감소_예외_테스트() {
        //given
        String name = "asd";
        Integer price = 1000;
        Integer quantity = 1000;
        List<String> fields = List.of("asd", "1", "1", "2024-11-01", "2024-11-01");
        Product product = new Product(name, price, quantity, Promotion.from(fields));
        //when
        //then
        assertThatThrownBy(() -> product.decreaseQuantity(1001)).isInstanceOf(OutOfStockException.class);
    }
}