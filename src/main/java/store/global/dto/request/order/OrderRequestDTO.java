package store.global.dto.request.order;

import java.util.List;

public record OrderRequestDTO(List<OrderProductInfo> orderProductInfos, Boolean isMembership) {
}
