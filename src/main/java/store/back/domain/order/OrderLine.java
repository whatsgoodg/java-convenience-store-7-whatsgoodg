package store.back.domain.order;

public class OrderLine {
    private final String productName;
    private final Integer quantity;
    private OrderLineStatus orderLineStatus;

    public OrderLine(String productName, Integer quantity, OrderLineStatus orderLineStatus) {
        this.productName = productName;
        this.quantity = quantity;
        this.orderLineStatus = orderLineStatus;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderLineStatus getOrderLineStatus() {
        return orderLineStatus;
    }

    public void setOrderLineStatus(OrderLineStatus orderLineStatus) {
        this.orderLineStatus = orderLineStatus;
    }
}
