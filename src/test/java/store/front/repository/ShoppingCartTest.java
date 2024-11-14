package store.front.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.global.exception.InvalidProductNameException;

class ShoppingCartTest {

    private final ShoppingCart shoppingCart = new ShoppingCart();

    @BeforeEach
    void setUp() {
        shoppingCart.clear();
    }

    @Test
    @DisplayName("쇼핑 카트에 상품 이름과 수량 추가 테스트")
    void 쇼핑_카트_상품_추가_테스트() {
        //then
        String productName = "콜라";
        Integer quantity = 2;
        //when
        shoppingCart.save(productName, quantity);
        Map<String, Integer> productInfos = shoppingCart.getProductInfos();
        //then
        assertThat(productInfos.get(productName)).isEqualTo(quantity);
    }

    @Test
    @DisplayName("쇼핑 카트에 상품 수량 감소 테스트")
    void 쇼핑_카트_상품_수량_감소_테스트() {
        //then
        String productName = "콜라";
        Integer quantity = 2;
        //when
        shoppingCart.save(productName, quantity);
        shoppingCart.decreaseQuantity(productName, 1);
        Map<String, Integer> productInfos = shoppingCart.getProductInfos();
        //then
        assertThat(productInfos.get(productName)).isEqualTo(1);
    }

    @Test
    @DisplayName("쇼핑 카트에 상품 수량 증가 테스트")
    void 쇼핑_카트_상품_수량_증가_테스트() {
        //then
        String productName = "콜라";
        Integer quantity = 2;
        //when
        shoppingCart.save(productName, quantity);
        shoppingCart.increaseQuantity(productName, 1);
        Map<String, Integer> productInfos = shoppingCart.getProductInfos();
        //then
        assertThat(productInfos.get(productName)).isEqualTo(3);
    }

    @Test
    @DisplayName("쇼핑 카트에 존재하지 않는 상품 수량 증가시 예외 발생")
    void 쇼핑_카트_상품_수량_증가_예외_테스트() {
        //then
        String productName = "콜라";
        Integer quantity = 2;
        String invalidProductName = "사이다";
        //when
        shoppingCart.save(productName, quantity);
        //then
        assertThatThrownBy(() -> shoppingCart.increaseQuantity(invalidProductName, 1)).isInstanceOf(
                InvalidProductNameException.class);
    }

    @Test
    @DisplayName("쇼핑 카트에 존재하지 않는 상품 수량 감소시 예외 발생")
    void 쇼핑_카트_상품_수량_감소_예외_테스트() {
        //then
        String productName = "콜라";
        Integer quantity = 2;
        String invalidProductName = "사이다";
        //when
        shoppingCart.save(productName, quantity);
        //then
        assertThatThrownBy(() -> shoppingCart.increaseQuantity(invalidProductName, 1)).isInstanceOf(
                InvalidProductNameException.class);
    }
}