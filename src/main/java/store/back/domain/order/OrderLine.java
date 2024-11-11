package store.back.domain.order;

public class OrderLine {
    private final String productName;
    private final Integer quantity;
    private final Integer price;
    private OrderLineStatus orderLineStatus;

    public OrderLine(final String productName, final Integer quantity, final Integer price, final OrderLineStatus orderLineStatus) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.orderLineStatus = orderLineStatus;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public OrderLineStatus getOrderLineStatus() {
        return orderLineStatus;
    }

    public void setOrderLineStatus(final OrderLineStatus orderLineStatus) {
        this.orderLineStatus = orderLineStatus;
    }
}
