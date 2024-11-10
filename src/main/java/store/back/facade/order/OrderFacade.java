package store.back.facade.order;

import java.util.List;
import store.back.domain.invoice.Invoice;
import store.back.domain.order.OrderLine;
import store.back.service.order.OrderLineService;
import store.back.service.order.OrderService;
import store.global.dto.request.order.OrderRequestDTO;
import store.global.dto.response.order.InvoiceInfo;
import store.global.dto.response.order.OrderResponseDTO;

public class OrderFacade {
    private final OrderService orderService = new OrderService();
    private final OrderLineService orderLineService = new OrderLineService();

    public OrderResponseDTO orderProducts(OrderRequestDTO orderRequestDTO) {
        List<OrderLine> orderLines = orderLineService.generateOrderLines(orderRequestDTO);
        Invoice invoice = orderService.orderProducts(orderLines);
        return new OrderResponseDTO(InvoiceInfo.from(invoice));
    }
}
