package com.ruber.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Shipment {
    private String address;
    private BigDecimal cost;

    private Shipment() {
    }

    public static Shipment buildFromEntity(com.ruber.dao.entity.Shipment entity) {
        Shipment shipment = new Shipment();

        shipment.setAddress(entity.getAddress());
        shipment.setCost(entity.getCost());

        return shipment;
    }

    public com.ruber.dao.entity.Shipment toEntity() {
        return new com.ruber.dao.entity.Shipment(null, address, cost);
    }
}
