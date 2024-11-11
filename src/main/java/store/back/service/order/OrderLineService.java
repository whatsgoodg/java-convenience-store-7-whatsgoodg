package store.back.service.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.back.domain.order.OrderLine;
import store.back.domain.order.OrderLineStatus;
import store.back.domain.product.Product;
import store.back.service.product.ProductQueryService;
import store.global.dto.request.order.OrderProductInfo;
import store.global.dto.request.order.OrderRequestDTO;
import store.global.exception.NoPurchaseProductException;

public class OrderLineService {
    private final ProductQueryService productQueryService = new ProductQueryService();

    public List<OrderLine> generateOrderLines(final OrderRequestDTO orderRequestDTO) {
        List<OrderProductInfo> orderProductInfos = orderRequestDTO.orderProductInfos();
        List<OrderLine> orderLines = new ArrayList<>();
        orderProductInfos.forEach(orderProductInfo -> {
            List<OrderLine> newOrderLines = createOrderLinesBeforeMembership(orderProductInfo);
            markMemberShip(newOrderLines, orderRequestDTO.isMembership());
            orderLines.addAll(newOrderLines);
        });
        return orderLines.stream().toList();
    }

    private void markMemberShip(final List<OrderLine> orderProductInfos, Boolean isMembership) {
        if (!isMembership) {
            return;
        }
        orderProductInfos.forEach(orderLine -> {
            if (orderLine.getOrderLineStatus() == OrderLineStatus.NONE) {
                orderLine.setOrderLineStatus(OrderLineStatus.MEMBERSHIP);
            }
        });

    }

    private List<OrderLine> createOrderLinesBeforeMembership(final OrderProductInfo orderProductInfo) {
        List<OrderLine> orderLines = new ArrayList<>();
        Optional<Product> productWithPromotion = productQueryService.findProductWithPromotion(orderProductInfo.name());
        productWithPromotion.ifPresentOrElse(product -> {
            List<OrderLine> promotionalOrderLines = createPromotionalOrderLines(orderProductInfo, product);
            promotionalOrderLines.addAll(createOrderLineAfterPromotion(orderProductInfo, product));
            orderLines.addAll(promotionalOrderLines);
        }, () -> {
            orderLines.add(createNonPromotionalProduct(orderProductInfo));
        });
        return orderLines;
    }

    private List<OrderLine> createPromotionalOrderLines(final OrderProductInfo orderProductInfo,
                                                        final Product product) {
        Integer promotionCount = calculatePromotionCount(orderProductInfo, product);
        List<OrderLine> orderLines = new ArrayList<>();
        if (promotionCount > 0) {
            int buyQuantity = promotionCount * product.getPromotion().getBuy();
            int getQuantity = promotionCount * product.getPromotion().getGet();
            orderLines.add(new OrderLine(orderProductInfo.name(), buyQuantity, product.getPrice() * buyQuantity,
                    OrderLineStatus.PROMOTION));
            orderLines.add(new OrderLine(orderProductInfo.name(), getQuantity, product.getPrice() * getQuantity,
                    OrderLineStatus.FREEBIE));
        }
        return orderLines;
    }

    private List<OrderLine> createOrderLineAfterPromotion(final OrderProductInfo orderProductInfo,
                                                          final Product product) {
        List<OrderLine> orderLines = new ArrayList<>();
        Integer nonPromotionQuantity = calculateNonPromotionQuantity(orderProductInfo, product);
        if (nonPromotionQuantity > 0) {
            orderLines.add(new OrderLine(orderProductInfo.name(), nonPromotionQuantity,
                    product.getPrice() * nonPromotionQuantity, OrderLineStatus.NONE));
        }
        return orderLines;
    }

    private OrderLine createNonPromotionalProduct(final OrderProductInfo orderProductInfo) {
        Optional<Product> productNoPromotion = productQueryService.findProductNoPromotion(orderProductInfo.name());
        if (productNoPromotion.isEmpty()) {
            throw new NoPurchaseProductException();
        }
        return new OrderLine(orderProductInfo.name(), orderProductInfo.quantity(),
                orderProductInfo.quantity() * productNoPromotion.get().getPrice(), OrderLineStatus.NONE);
    }

    private Integer calculatePromotionCount(final OrderProductInfo orderProductInfo, final Product product) {
        int buyAndGet = product.getPromotion().getBuy() + product.getPromotion().getGet();

        int orderPromotionCount = orderProductInfo.quantity() / buyAndGet;
        int productPromotionCount = product.getQuantity() / buyAndGet;
        return Math.min(orderPromotionCount, productPromotionCount);
    }

    private Integer calculateNonPromotionQuantity(final OrderProductInfo orderProductInfo, final Product product) {
        int buyAndGet = product.getPromotion().getBuy() + product.getPromotion().getGet();

        Integer promotionCount = calculatePromotionCount(orderProductInfo, product);
        int nonPromotionQuantity = orderProductInfo.quantity() - promotionCount * buyAndGet;

        return Math.max(nonPromotionQuantity, 0);
    }
}
