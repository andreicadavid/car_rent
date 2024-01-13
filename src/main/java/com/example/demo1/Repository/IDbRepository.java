package com.example.demo1.Repository;



import com.example.demo1.Domain.Entity;

import java.util.ArrayList;

public interface IDbRepository<T extends Entity> extends RepositoryInterface<T>{

    void openConnection();
    void closeConnection();
    void createTable();
    void initTable();
    ArrayList<T> getAll();
    void add(T t);

}
