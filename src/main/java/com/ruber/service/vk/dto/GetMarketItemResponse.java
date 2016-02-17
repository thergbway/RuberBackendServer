package com.ruber.service.vk.dto;

import com.ruber.controller.dto.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMarketItemResponse {
    @Data
    public static class Response{
        private Integer count;
        private Item[] items;
    }

    private Response response;
}