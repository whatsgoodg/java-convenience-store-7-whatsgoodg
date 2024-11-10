package store.global.dto.response.order;

import java.util.List;

public record OrderRequestDTO(List<OrderProductInfo> orderProductInfos, Boolean isMembership) {
}
