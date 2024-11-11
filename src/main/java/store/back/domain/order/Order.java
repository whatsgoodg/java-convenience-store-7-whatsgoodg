package store.back.domain.order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import store.back.domain.invoice.FreebieProduct;
import store.back.domain.invoice.Invoice;
import store.back.domain.invoice.OrderedProduct;
import store.global.exception.NoPurchaseProductException;

public class Order {
    private final List<OrderLine> orderLines;

    public Order(List<OrderLine> orderLines) {
        validate(orderLines);
        this.orderLines = orderLines;
    }

    private void validate(final List<OrderLine> orderLines) {
        if (orderLines.isEmpty()) {
            throw new NoPurchaseProductException();
        }
    }

    public Invoice order() {
        Map<String, List<OrderLine>> orderLinesGroupedByName = orderLines.stream()
                .collect(Collectors.groupingBy(OrderLine::getProductName));
        List<OrderedProduct> orderedProducts = getOrderedProducts(orderLinesGroupedByName);
        List<FreebieProduct> freebieProducts = getFreebieProducts(orderLinesGroupedByName);
        Integer totalPrice = calculateTotalPrice();
        Integer totalQuantity = calculateTotalQuantity();
        Integer freebieAmount = calculateFreebieAmount();
        Double memberShipAmount = calculateMembershipAmount();
        return new Invoice(orderedProducts, freebieProducts, totalPrice, totalQuantity, freebieAmount,
                memberShipAmount.intValue(), (int) (totalPrice - freebieAmount - memberShipAmount));
    }

    private List<OrderedProduct> getOrderedProducts(final Map<String, List<OrderLine>> orderLinesGroupedByName) {
        List<OrderedProduct> orderedProducts = new ArrayList<>();
        for (Entry<String, List<OrderLine>> entry : orderLinesGroupedByName.entrySet()) {
            int summedQuantity = entry.getValue().stream().mapToInt(OrderLine::getQuantity).sum();
            int summedPrice = entry.getValue().stream().mapToInt(OrderLine::getPrice).sum();
            orderedProducts.add(new OrderedProduct(entry.getKey(), summedQuantity, summedPrice));
        }
        orderedProducts.sort(Comparator.comparingInt(OrderedProduct::getPrice));
        return orderedProducts;
    }

    private List<FreebieProduct> getFreebieProducts(final Map<String, List<OrderLine>> orderLinesGroupedByName) {
        List<FreebieProduct> freebieProducts = new ArrayList<>();
        for (Entry<String, List<OrderLine>> entry : orderLinesGroupedByName.entrySet()) {
            int summedQuantity = entry.getValue().stream()
                    .filter(orderLine -> orderLine.getOrderLineStatus() == OrderLineStatus.FREEBIE)
                    .mapToInt(OrderLine::getQuantity).sum();
            if (summedQuantity > 0) {
                freebieProducts.add(new FreebieProduct(entry.getKey(), summedQuantity));
            }
        }
        freebieProducts.sort(Comparator.comparingInt(FreebieProduct::getQuantity));
        return freebieProducts;
    }

    private Integer calculateTotalPrice() {
        return orderLines.stream().mapToInt(OrderLine::getPrice).sum();
    }

    private Integer calculateTotalQuantity() {
        return orderLines.stream().mapToInt(OrderLine::getQuantity).sum();
    }

    private Integer calculateFreebieAmount() {
        return orderLines.stream().filter(orderLine -> orderLine.getOrderLineStatus() == OrderLineStatus.FREEBIE)
                .mapToInt(OrderLine::getPrice).sum();
    }

    private Double calculateMembershipAmount() {
        double membershipAmount =
                orderLines.stream().filter(orderLine -> orderLine.getOrderLineStatus() == OrderLineStatus.MEMBERSHIP)
                        .mapToDouble(OrderLine::getPrice).sum() * OrderLineStatus.MEMBERSHIP.getDiscountRate();
        return Math.min(membershipAmount, 8000.0);
    }
}
