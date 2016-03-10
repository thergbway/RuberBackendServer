package com.ruber.dao;

public interface GenericDAO<Entity> {

    void create(Entity e);

    Entity read(Integer id);

    void update(Entity e);

    void delete(Integer id);
}