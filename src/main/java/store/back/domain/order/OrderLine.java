package store.back.domain.order;

public class OrderLine {
    private final String productName;
    private final Integer quantity;
    private final Integer price;
    private OrderLineStatus orderLineStatus;

    public OrderLine(String productName, Integer quantity, Integer price, OrderLineStatus orderLineStatus) {
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

    public void setOrderLineStatus(OrderLineStatus orderLineStatus) {
        this.orderLineStatus = orderLineStatus;
    }
}
