package com.ruber.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.ruber.controller.dto.Order;
import com.ruber.controller.dto.OrderPreview;
import com.ruber.dao.entity.OrderStatus;
import com.ruber.exception.InvalidRequestJsonException;
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

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(OrdersController.PATH)
public class OrdersController {
    public static final String PATH = "/orders";

    @Autowired
    private OrdersService ordersService;

    @Value("WEB-INF/schema/orderUpdateSchema.json")//fixme can be removed because it needs only once in post construct
    private Resource orderUpdateSchemaFile;

    private JsonSchema orderUpdateSchema;

    private JsonNode orderUpdateSchemaAsJsonNode;

    @PostConstruct
    private void postConstruct() throws Exception {
        JsonNode jsonNode = JsonLoader.fromFile(orderUpdateSchemaFile.getFile());

        ArrayNode statusEnums = (ArrayNode) (jsonNode.get("properties").get("status").get("enum"));
        for (OrderStatus orderStatus : OrderStatus.values()) {
            statusEnums.add(orderStatus.toString());
        }

        orderUpdateSchemaAsJsonNode = jsonNode;
        orderUpdateSchema = JsonSchemaFactory.byDefault().getJsonSchema(orderUpdateSchemaAsJsonNode);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Order> addOrder(
        @RequestBody(required = true) Order order,
        @ModelAttribute("user_id") Integer userId,

        UriComponentsBuilder builder) {

        Integer orderId = ordersService.addOrder(userId, order);

        UriComponents uriComponents = builder
            .path(PATH + "/{id}")
            .buildAndExpand(orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        Order addedOrder = ordersService.getOrder(userId, orderId);

        return new ResponseEntity<>(addedOrder, headers, HttpStatus.CREATED);
    }


    @RequestMapping(method = GET)
    public List<OrderPreview> getOrdersPreview(@ModelAttribute("user_id") Integer userId) {
        return ordersService.getOrdersPreview(userId);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Order getOrder(
        @PathVariable("id") Integer orderId,
        @ModelAttribute("user_id") Integer userId) {

        return ordersService.getOrder(userId, orderId);
    }

    @RequestMapping(value = "/{id}", method = PUT)
    @ResponseStatus(HttpStatus.OK)
    public Order updateOrder(
        @PathVariable("id") Integer orderId,
        @RequestBody(required = true) JsonNode updateInfo,
        @ModelAttribute("user_id") Integer userId
    ) {
        validateUpdateOrderJsonNode(updateInfo);

        ordersService.updateOrder(userId, orderId, updateInfo);

        return ordersService.getOrder(userId, orderId);
    }

    private void validateUpdateOrderJsonNode(JsonNode node) {
        try {
            if (!orderUpdateSchema.validate(node).isSuccess()) {
                throw new InvalidRequestJsonException("valid schema: "
                    + new ObjectMapper().writeValueAsString(orderUpdateSchemaAsJsonNode));
            }
        } catch (ProcessingException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}