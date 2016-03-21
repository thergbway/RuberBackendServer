package com.ruber.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.ruber.controller.dto.AddOrderRequest;
import com.ruber.controller.dto.GetOrderResponse;
import com.ruber.controller.dto.OrderPreview;
import com.ruber.dao.entity.OrderStatus;
import com.ruber.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(OrdersController.PATH)
public class OrdersController {
    public static final String PATH = "/orders";

    @Value("WEB-INF/schema/orderUpdateSchema.json")//fixme can be removed because it needs only once in post construct
    private Resource orderUpdateSchemaFile;

    private JsonSchema orderUpdateSchema;

    @PostConstruct
    private void postConstruct() throws Exception {
        JsonNode jsonNode = JsonLoader.fromFile(orderUpdateSchemaFile.getFile());

        ArrayNode statusEnums = (ArrayNode) (jsonNode.get("properties").get("status").get("enum"));
        for (OrderStatus orderStatus : OrderStatus.values()) {
            statusEnums.add(orderStatus.toString());
        }

        orderUpdateSchema = JsonSchemaFactory.byDefault().getJsonSchema(jsonNode);
    }

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

    @RequestMapping
    public List<OrderPreview> getOrdersPreview(
        @RequestParam(value = "access_token", required = true) String accessToken) {

        return ordersService.getOrdersPreview(accessToken);
    }

    @RequestMapping("/{id}")
    public GetOrderResponse getOrder(
        @RequestParam(value = "access_token", required = true) String accessToken,
        @PathVariable("id") Integer orderId) {

        return ordersService.getOrder(accessToken, orderId);
    }

    @RequestMapping(value = "/{id}", method = PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateOrder(
        @RequestParam(value = "access_token", required = true) String accessToken,
        @PathVariable("id") Integer orderId,
        @RequestBody(required = true) JsonNode updateInfo
    ) {
        validateUpdateOrderJsonNode(updateInfo);

        ordersService.updateOrder(accessToken, orderId, updateInfo);
    }

    private void validateUpdateOrderJsonNode(JsonNode node) {
        try {
            if (!orderUpdateSchema.validate(node).isSuccess()) {
                throw new RuntimeException("Invalid Json");
            }
        } catch (ProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}