package store.back.domain.product;

import store.global.exception.OutOfStockException;

public class Product {
    private final Integer id;
    private final String name;
    private final Integer price;
    private Integer quantity;
    private final Promotion promotion;

    public Product(final Integer id, final String name, final Integer price, final Integer quantity,
                   final Promotion promotion) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public void decreaseQuantity(final Integer quantity) {
        if (this.quantity - quantity < 0) {
            throw new OutOfStockException();
        }
        this.quantity = this.quantity - quantity;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
