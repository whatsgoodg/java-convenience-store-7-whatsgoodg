package store.back.domain.invoice;

import java.util.List;

public class Invoice {
    private final List<OrderedProduct> orderedProducts;
    private final List<FreebieProduct> freebieProducts;
    private final Integer totalPrice;
    private final Integer totalQuantity;
    private final Integer freebieAmount;
    private final Integer membershipAmount;
    private final Integer priceToPay;

    public Invoice(final List<OrderedProduct> orderedProducts, final List<FreebieProduct> freebieProducts,
                   final Integer totalPrice, final Integer totalQuantity, final Integer freebieAmount,
                   final Integer membershipAmount, final Integer priceToPay) {
        this.orderedProducts = orderedProducts;
        this.freebieProducts = freebieProducts;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.freebieAmount = freebieAmount;
        this.membershipAmount = membershipAmount;
        this.priceToPay = priceToPay;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public List<FreebieProduct> getFreebieProducts() {
        return freebieProducts;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public Integer getFreebieAmount() {
        return freebieAmount;
    }

    public Integer getMembershipAmount() {
        return membershipAmount;
    }

    public Integer getPriceToPay() {
        return priceToPay;
    }
}
