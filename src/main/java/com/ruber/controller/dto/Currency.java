package com.ruber.controller.dto;

public class Currency {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency(Integer id, String name) {

        this.id = id;
        this.name = name;
    }

    public Currency() {

    }
}