package com.ruber.dao;

//TODO попробовать вынести логику в этот абстрактный класс
public interface GenericDAO<Entity> {

    void create(Entity e);

    Entity read(Integer id);

    void update(Entity e);

    void delete(Integer id);
}