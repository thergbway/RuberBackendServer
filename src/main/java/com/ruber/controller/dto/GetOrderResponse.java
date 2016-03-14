package com.ruber.controller.dto;

import com.ruber.dao.entity.Order;
import com.ruber.dao.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponse {
    private Integer id;
    private Integer owner_id;
    private Integer customer_id;
    private String title;
    private Long created_timestamp;
    private Long deadline_timestamp;
    private Integer shipment_cost;
    private OrderStatus status;
    private List<OrderPosition> order_positions;
    private List<Integer> pinned_message_ids;
    private List<String> pinned_texts;

    public static GetOrderResponse buildFromOrder(Order order) {
        List<OrderPosition> positions = order
            .getOrderPositions()
            .stream()
            .map(OrderPosition::buildFromOrderPosition)
            .collect(Collectors.toList());

        return new GetOrderResponse(
            order.getId(),
            order.getOwnerId(),
            order.getCustomerId(),
            order.getTitle(),
            order.getCreatedTimestamp(),
            order.getDeadlineTimestamp(),
            order.getShipmentCost(),
            order.getStatus(),
            positions,
            order.getPinnedMessageIds(),
            order.getPinnedTexts()
        );
    }
}

