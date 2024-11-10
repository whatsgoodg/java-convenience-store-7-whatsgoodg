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

public class OrderLineService {
    private final ProductQueryService productQueryService = new ProductQueryService();

    public List<OrderLine> generateOrderLines(OrderRequestDTO orderRequestDTO) {
        List<OrderProductInfo> orderProductInfos = orderRequestDTO.orderProductInfos();
        List<OrderLine> orderLines = new ArrayList<>();
        orderProductInfos.forEach(orderProductInfo -> {
            List<OrderLine> newOrderLines = createOrderLinesBeforeMembership(orderProductInfo);
            markMemberShip(newOrderLines, orderRequestDTO.isMembership());
            orderLines.addAll(newOrderLines);
        });
        return orderLines.stream().toList();
    }

    private void markMemberShip(List<OrderLine> orderProductInfos, Boolean isMembership){
        if(!isMembership){
            return;
        }
        orderProductInfos.forEach(orderLine -> {
            if(orderLine.getOrderLineStatus() == OrderLineStatus.NONE){
                orderLine.setOrderLineStatus(OrderLineStatus.MEMBERSHIP);
            }
        });

    }

    private List<OrderLine> createOrderLinesBeforeMembership(OrderProductInfo orderProductInfo) {
        List<OrderLine> orderLines = new ArrayList<>();
        Optional<Product> productWithPromotion = productQueryService.findProductWithPromotion(orderProductInfo.name());
        productWithPromotion.ifPresentOrElse(product -> {
            List<OrderLine> promotionalOrderLines = createPromotionalOrderLines(orderProductInfo, product);
            promotionalOrderLines.add(createOrderLineAfterPromotion(orderProductInfo, product));
            orderLines.addAll(promotionalOrderLines);
        }, () -> {
            orderLines.add(new OrderLine(orderProductInfo.name(), orderProductInfo.quantity(), OrderLineStatus.NONE));
        });
        return orderLines;
    }


    private List<OrderLine> createPromotionalOrderLines(OrderProductInfo orderProductInfo, Product product) {
        Integer promotionCount = calculatePromotionCount(orderProductInfo, product);

        List<OrderLine> orderLines = new ArrayList<>();
        if (promotionCount > 0) {
            int buyQuantity = promotionCount * product.getPromotion().getBuy();
            int getQuantity = promotionCount * product.getPromotion().getGet();
            orderLines.add(new OrderLine(orderProductInfo.name(), buyQuantity, OrderLineStatus.PROMOTION));
            orderLines.add(new OrderLine(orderProductInfo.name(), getQuantity, OrderLineStatus.FREEBIE));
        }
        return orderLines;
    }

    private OrderLine createOrderLineAfterPromotion(OrderProductInfo orderProductInfo, Product product) {
        Integer nonPromotionQuantity = calculateNonPromotionQuantity(orderProductInfo, product);
        return new OrderLine(orderProductInfo.name(), nonPromotionQuantity, OrderLineStatus.NONE);
    }

    private Integer calculatePromotionCount(OrderProductInfo orderProductInfo, Product product) {
        int buyAndGet = product.getPromotion().getBuy() + product.getPromotion().getGet();

        int orderPromotionCount = orderProductInfo.quantity() / buyAndGet;
        int productPromotionCount = product.getQuantity() / buyAndGet;
        return Math.min(orderPromotionCount, productPromotionCount);
    }

    private Integer calculateNonPromotionQuantity(OrderProductInfo orderProductInfo, Product product) {
        int buyAndGet = product.getPromotion().getBuy() + product.getPromotion().getGet();

        Integer promotionCount = calculatePromotionCount(orderProductInfo, product);
        int nonPromotionQuantity = orderProductInfo.quantity() - promotionCount * buyAndGet;

        return Math.max(nonPromotionQuantity, 0);
    }
}
