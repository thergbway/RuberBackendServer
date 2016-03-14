package com.ruber.controller.dto;

import com.ruber.dao.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderRequest {
    private String title;
    private Integer customer_id;
    private Long created_timestamp;
    private Long deadline_timestamp;
    private Integer shipment_cost;
    private OrderStatus status;
    private List<OrderPosition> order_positions;
}