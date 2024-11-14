package store.front.repository;

import java.util.HashMap;
import java.util.Map;
import store.global.exception.InvalidProductNameException;

public class ShoppingCart {
    private final Map<String, Integer> productInfos;
    private Boolean isMembership;

    public ShoppingCart() {
        this.productInfos = new HashMap<>();
        this.isMembership = false;
    }

    public void save(final String name, final Integer quantity) {
        productInfos.put(name, quantity);
    }

    public void increaseQuantity(final String name, final Integer quantity) {
        if (!productInfos.containsKey(name)) {
            throw new InvalidProductNameException("상품이 장바구니에 존재하지 않습니다.");
        }
        productInfos.put(name, productInfos.get(name) + quantity);
    }

    public void decreaseQuantity(final String name, final Integer quantity) {
        if (!productInfos.containsKey(name)) {
            throw new InvalidProductNameException("상품이 장바구니에 존재하지 않습니다.");
        }
        productInfos.put(name, productInfos.get(name) - quantity);
    }

    public void setMembership() {
        this.isMembership = true;
    }

    public Map<String, Integer> getProductInfos() {
        return productInfos;
    }

    public Boolean getMembership() {
        return isMembership;
    }

    public void clear() {
        this.productInfos.clear();
        this.isMembership = false;
    }
}
