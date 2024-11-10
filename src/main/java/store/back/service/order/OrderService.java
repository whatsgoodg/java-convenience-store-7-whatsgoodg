package store.back.service.order;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import store.back.domain.invoice.Invoice;
import store.back.domain.order.Order;
import store.back.domain.order.OrderLine;
import store.back.domain.product.Product;
import store.back.service.product.ProductQueryService;

public class OrderService {
    private final ProductQueryService productQueryService = new ProductQueryService();

    public Invoice orderProducts(final List<OrderLine> orderLines) {
        Order order = new Order(orderLines);
        Invoice invoice = order.order();
        updateStock(orderLines);
        return invoice;
    }

    public void updateStock(final List<OrderLine> orderLines) {
        Map<String, List<OrderLine>> orderLinesGroupedByName = orderLines.stream()
                .collect(Collectors.groupingBy(OrderLine::getProductName));
        for (Entry<String, List<OrderLine>> entry : orderLinesGroupedByName.entrySet()) {
            int totalQuantity = entry.getValue().stream().mapToInt(OrderLine::getQuantity).sum();
            updateProductStock(entry, totalQuantity);
        }
    }

    private void updateProductStock(final Entry<String, List<OrderLine>> entry, final int totalQuantity) {
        Optional<Product> productWithPromotion = productQueryService.findProductWithPromotion(entry.getKey());
        int decreaseQuantity = totalQuantity;
        if (productWithPromotion.isPresent()) {
            decreaseQuantity = decreaseQuantity(productWithPromotion.get(), decreaseQuantity);
        }
        Optional<Product> productNoPromotion = productQueryService.findProductNoPromotion(entry.getKey());
        if (productNoPromotion.isPresent()) {
            decreaseQuantity(productNoPromotion.get(), decreaseQuantity);
        }
    }

    private Integer decreaseQuantity(final Product product, final Integer quantity) {
        if (quantity <= 0) {
            return 0;
        }
        int decreasedQuantity = quantity;
        if (product.getQuantity() - decreasedQuantity < 0) {
            decreasedQuantity = product.getQuantity();
        }
        product.decreaseQuantity(decreasedQuantity);
        return quantity - decreasedQuantity;
    }
}
