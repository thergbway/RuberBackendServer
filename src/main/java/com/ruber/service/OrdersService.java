package com.ruber.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Function;
import com.ruber.controller.dto.AddOrderRequest;
import com.ruber.controller.dto.GetOrderResponse;
import com.ruber.controller.dto.OrderPreview;
import com.ruber.dao.OrderDAO;
import com.ruber.dao.UserDAO;
import com.ruber.dao.entity.*;
import com.ruber.exception.InvalidAccessTokenException;
import com.ruber.exception.InvalidURLException;
import com.ruber.exception.NoSuchOrderException;
import com.ruber.exception.NotEnoughArgumentsException;
import com.ruber.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
@Transactional//TODO
public class OrdersService {
    @Autowired
    private RuberTokensService ruberTokensService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrderDAO orderDAO;

    public Integer addOrder(String accessToken, AddOrderRequest addOrderRequest) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new InvalidAccessTokenException();
        }

        User user = userDAO.getByRuberToken(accessToken);

        Long createdTimestamp = TimeUtils.getCurrentTimestamp();

        Order order = addOrderRequest.toOrder(createdTimestamp);

        user
            .getOrders()
            .add(order);

        orderDAO.create(order);

        return order.getId();
    }

    public GetOrderResponse getOrder(String accessToken, Integer orderId) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new InvalidAccessTokenException();
        }

        //TODO it is better to create a named query that gets order by Id for the specified user
        User user = userDAO.getByRuberToken(accessToken);

        List<Order> orders = user
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new NoSuchOrderException(orderId);

        Order order = orders.get(0);

        return GetOrderResponse.buildFromOrder(order);
    }

    public List<OrderPreview> getOrdersPreview(String accessToken) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new InvalidAccessTokenException();
        }

        User user = userDAO.getByRuberToken(accessToken);

        return user
            .getOrders()
            .stream()
            .map(OrderPreview::buildFromEntity)
            .collect(Collectors.toList());
    }

    public void updateOrder(String accessToken, Integer orderId, JsonNode updateInfo) {//fixme refactor: create several methods
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new InvalidAccessTokenException();
        }

        //TODO it is better to create a named query that gets order by Id for the specified user
        User user = userDAO.getByRuberToken(accessToken);

        List<Order> orders = user
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new NoSuchOrderException(orderId);

        Order order = orders.get(0);

        Function<JsonNode, Long> getAsLongOrNull = (JsonNode longNode) ->
            longNode.isNull() ? null : longNode.asLong();

        Function<JsonNode, Integer> getAsIntegerOrNull = (JsonNode integerNode) ->
            integerNode.isNull() ? null : integerNode.asInt();

        Function<JsonNode, String> getAsTextIfNotNull = (JsonNode textNode) ->
            textNode == null ? null : textNode.asText();

        if (updateInfo.has("title"))
            order.setTitle(updateInfo.get("title").asText());

        if (updateInfo.has("description"))
            order.setDescription(updateInfo.get("description").asText());

        if (updateInfo.has("status"))
            order.setStatus(OrderStatus.valueOf(updateInfo.get("status").asText()));

        if (updateInfo.has("deadline_timestamp"))
            order.setDeadlineTimestamp(getAsLongOrNull.apply(updateInfo.get("deadline_timestamp")));

        if (updateInfo.has("discount")) {
            JsonNode discountInfo = updateInfo.get("discount");

            if (discountInfo.isNull()) {
                order.setDiscount(null);
            } else {
                String discountTitle = getAsTextIfNotNull.apply(discountInfo.get("title"));
                String discountDescription = getAsTextIfNotNull.apply(discountInfo.get("description"));
                String discountThumbPhoto = getAsTextIfNotNull.apply(discountInfo.get("thumb_photo"));
                String discountCostAsText = getAsTextIfNotNull.apply(discountInfo.get("cost"));

                BigDecimal discountCost =
                    discountCostAsText != null ? new BigDecimal(discountCostAsText).setScale(2).negate() : null;

                try {
                    Discount discount = order.getDiscount();
                    if (discount != null) {
                        if (discountTitle != null)
                            discount.setTitle(discountTitle);

                        if (discountDescription != null)
                            discount.setDescription(discountDescription);

                        if (discountThumbPhoto != null)
                            discount.setThumbPhoto(new URL(discountThumbPhoto));

                        if (discountCost != null)
                            discount.setCost(discountCost);
                    } else {
                        if (discountTitle == null ||
                            discountDescription == null ||
                            discountThumbPhoto == null ||
                            discountCost == null)
                            throw new NotEnoughArgumentsException(asList(
                                "discount title", "discount description", "discount thumb photo", "discount cost"
                            ));

                        discount = new Discount(
                            null,
                            discountTitle,
                            discountDescription,
                            new URL(discountThumbPhoto),
                            discountCost);

                        order.setDiscount(discount);
                    }
                } catch (MalformedURLException e) {
                    throw new InvalidURLException(discountThumbPhoto);
                }
            }
        }

        if (updateInfo.has("shipment")) {
            JsonNode shipmentInfo = updateInfo.get("shipment");
            if (shipmentInfo.isNull()) {
                order.setShipment(null);
            } else {
                String shipmentAddress = getAsTextIfNotNull.apply(shipmentInfo.get("address"));
                String shipmentCostAsText = getAsTextIfNotNull.apply(shipmentInfo.get("cost"));
                BigDecimal shipmentCost =
                    shipmentCostAsText != null ? new BigDecimal(shipmentCostAsText).setScale(2) : null;

                Shipment shipment = order.getShipment();
                if (shipment != null) {
                    if (shipmentAddress != null)
                        shipment.setAddress(shipmentAddress);

                    if (shipmentCost != null)
                        shipment.setCost(shipmentCost);
                } else {
                    if (shipmentAddress == null || shipmentCost == null)
                        throw new NotEnoughArgumentsException(asList("shipment address", "shipment cost"));

                    shipment = new Shipment(null, shipmentAddress, shipmentCost);

                    order.setShipment(shipment);
                }
            }
        }

        JsonNode customerInfo = updateInfo.get("customer");
        if (customerInfo != null) {
            Customer customer = order.getCustomer();

            if (customerInfo.has("name"))
                customer.setName(customerInfo.get("name").asText());

            if (customerInfo.has("phone"))
                customer.setPhone(customerInfo.get("phone").asText());

            if (customerInfo.has("vk_id"))
                customer.setVkId(getAsIntegerOrNull.apply(customerInfo.get("vk_id")));
        }
    }
}