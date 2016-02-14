package com.ruber.controller.dto;

public class Group {
    private Integer id;
    private Integer vkId;
    private String name;
    private Market market;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVkId() {
        return vkId;
    }

    public void setVkId(Integer vkId) {
        this.vkId = vkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Group(Integer id, Integer vkId, String name, Market market) {

        this.id = id;
        this.vkId = vkId;
        this.name = name;
        this.market = market;
    }

    public Group() {

    }
}
