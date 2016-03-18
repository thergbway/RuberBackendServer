package com.ruber.controller;

import com.ruber.controller.dto.AddOrderRequest;
import com.ruber.controller.dto.GetOrderResponse;
import com.ruber.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(OrdersController.PATH)
public class OrdersController {
    public static final String PATH = "/orders";

    @Autowired
    private OrdersService ordersService;

    @RequestMapping(method = POST)
    public ResponseEntity<Void> addOrder(
        @RequestParam(value = "access_token", required = true) String accessToken,
        @RequestBody(required = true) AddOrderRequest addOrderRequset,

        UriComponentsBuilder builder) {

        Integer orderId = ordersService.addOrder(accessToken, addOrderRequset);

        UriComponents uriComponents = builder
            .path(PATH + "/{id}")
            .buildAndExpand(orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping("/{id}")
    public GetOrderResponse getOrder(
        @RequestParam(value = "access_token", required = true) String accessToken,
        @PathVariable("id") Integer orderId) {

        return ordersService.getOrder(accessToken, orderId);
    }
}