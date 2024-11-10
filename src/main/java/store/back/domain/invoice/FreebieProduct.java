package store.back.domain.invoice;

public class FreebieProduct {
    private final String name;
    private final Integer quantity;

    public FreebieProduct(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
