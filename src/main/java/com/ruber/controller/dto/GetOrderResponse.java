package com.ruber.controller.dto;

import com.ruber.dao.entity.Order;
import com.ruber.dao.entity.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@Data
public class GetOrderResponse {
    private Integer id;
    private String title;
    private String description;
    private OrderStatus status;
    private Long created_timestamp;
    private Long deadline_timestamp;
    private Customer customer;
    private Shipment shipment;
    private Discount discount;
    private List<ItemReplica> item_replicas;
    private List<VkItemReplica> vk_item_replicas;

    private GetOrderResponse() {
    }

    public static GetOrderResponse buildFromOrder(Order o) {
        final Discount[] discount = {null};
        LinkedList<ItemReplica> itemReplicas = new LinkedList<>();
        LinkedList<VkItemReplica> vkItemReplicas = new LinkedList<>();

        o.getOrderPositions().forEach(position -> {
            if (position instanceof com.ruber.dao.entity.Discount)
                discount[0] = Discount.buildFromEntity((com.ruber.dao.entity.Discount) position);
            else if (position instanceof com.ruber.dao.entity.VkItemReplica)
                vkItemReplicas.add(VkItemReplica.buildFromEntity((com.ruber.dao.entity.VkItemReplica) position));
            else if (position instanceof com.ruber.dao.entity.ItemReplica)
                itemReplicas.add(ItemReplica.buildFromEntity((com.ruber.dao.entity.ItemReplica) position));
            else
                throw new RuntimeException("Invalid instance type for casting: " + position.getClass());
        });

        GetOrderResponse response = new GetOrderResponse();

        response.setId(o.getId());
        response.setTitle(o.getTitle());
        response.setDescription(o.getDescription());
        response.setStatus(o.getStatus());
        response.setCreated_timestamp(o.getCreatedTimestamp());
        response.setDeadline_timestamp(o.getDeadlineTimestamp());
        response.setCustomer(Customer.buildFromEntity(o.getCustomer()));

        if (o.getShipment() != null)
            response.setShipment(Shipment.buildFromEntity(o.getShipment()));

        response.setDiscount(discount[0]);
        response.setItem_replicas(itemReplicas);
        response.setVk_item_replicas(vkItemReplicas);

        return response;
    }

    @Data
    private static class Discount {
        private Integer id;
        private String title;
        private String description;
        private URL thumb_photo;
        private BigDecimal cost;

        private Discount() {
        }

        public static Discount buildFromEntity(com.ruber.dao.entity.Discount entity) {
            Discount discount = new Discount();

            discount.setId(entity.getId());
            discount.setTitle(entity.getTitle());
            discount.setDescription(entity.getDescription());
            discount.setThumb_photo(entity.getThumbPhoto());
            discount.setCost(entity.getCost().negate());

            return discount;
        }
    }

    @Data
    private static class Customer {
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
    }

    @Data
    private static class Shipment {
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
    }

    @Data
    private static class ItemReplica {
        private Integer id;
        private String title;
        private String description;
        private URL thumb_photo;
        private BigDecimal price;
        private Integer amount;

        private ItemReplica() {
        }

        public static ItemReplica buildFromEntity(com.ruber.dao.entity.ItemReplica entity) {
            ItemReplica itemReplica = new ItemReplica();

            itemReplica.setId(entity.getId());
            itemReplica.setTitle(entity.getTitle());
            itemReplica.setDescription(entity.getDescription());
            itemReplica.setThumb_photo(entity.getThumbPhoto());
            itemReplica.setPrice(entity.getPrice());
            itemReplica.setAmount(entity.getAmount());

            return itemReplica;
        }
    }

    @Data
    private static class VkItemReplica {
        private Integer id;
        private String title;
        private String description;
        private URL thumb_photo;
        private BigDecimal price;
        private Integer amount;
        private Integer vk_id;
        private Integer vk_owner_id;

        private VkItemReplica() {
        }

        public static VkItemReplica buildFromEntity(com.ruber.dao.entity.VkItemReplica entity) {
            VkItemReplica vkItemReplica = new VkItemReplica();

            vkItemReplica.setId(entity.getId());
            vkItemReplica.setTitle(entity.getTitle());
            vkItemReplica.setDescription(entity.getDescription());
            vkItemReplica.setThumb_photo(entity.getThumbPhoto());
            vkItemReplica.setPrice(entity.getPrice());
            vkItemReplica.setAmount(entity.getAmount());
            vkItemReplica.setVk_id(entity.getVkId());
            vkItemReplica.setVk_owner_id(entity.getVkOwnerId());

            return vkItemReplica;
        }
    }
}