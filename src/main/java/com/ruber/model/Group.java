package com.ruber.model;

public class Group {
    private Integer id;
    private Integer vk_id;
    private String name;
    private Market market;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVk_id() {
        return vk_id;
    }

    public void setVk_id(Integer vk_id) {
        this.vk_id = vk_id;
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

    public Group(Integer id, Integer vk_id, String name, Market market) {

        this.id = id;
        this.vk_id = vk_id;
        this.name = name;
        this.market = market;
    }

    public Group() {

    }
}
