package com.ruber.controller.dto;

import lombok.Data;

@Data
public class Customer {
    private String name;
    private String phone;
    private Integer vk_id;

    private Customer() {
    }

    public static Customer buildFromEntity(com.ruber.dao.entity.Customer entity) {
        Customer customer = new Customer();

        customer.setName(entity.getName());
        customer.setPhone(entity.getPhone());
        customer.setVk_id(entity.getVkId());

        return customer;
    }

    public com.ruber.dao.entity.Customer toEntity() {
        return new com.ruber.dao.entity.Customer(
            null,
            name,
            phone,
            vk_id
        );
    }
}