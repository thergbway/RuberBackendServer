package com.ruber.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruber.controller.dto.AddOrderRequest;
import com.ruber.controller.dto.GetOrderResponse;
import com.ruber.dao.entity.Order;
import com.ruber.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(OrdersController.PATH)
public class OrdersController {
    public static final String PATH = "/orders";

    @Autowired
    private OrdersService ordersService;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(AddOrderRequest.class,
            new PropertyEditorSupport() {
                @Override
                public void setAsText(String text) {
                    try {
                        setValue(new ObjectMapper().readValue(text, AddOrderRequest.class));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
    }

    @RequestMapping(value = "/{id}", method = GET)
    public GetOrderResponse getOrder(
        @PathVariable("id") Integer id,
        @RequestParam(value = "access_token", required = true) String accessToken) {

        Order order = ordersService.getOrder(accessToken, id);

        return GetOrderResponse.buildFromOrder(order);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> addOrder(
        @RequestParam(value = "access_token", required = true) String accessToken,
        @RequestBody(required = true) AddOrderRequest orderInfo,

        UriComponentsBuilder builder) {

        Integer orderId = ordersService.addOrder(accessToken, orderInfo);

        UriComponents uriComponents = builder
            .path(PATH + "/{id}")
            .buildAndExpand(orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}