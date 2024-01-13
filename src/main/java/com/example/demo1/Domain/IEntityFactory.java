package com.example.demo1.Domain;

public interface IEntityFactory<T extends Entity> {
    public T createEntity(String line);

    public String toStringEntity(T entity);
}
