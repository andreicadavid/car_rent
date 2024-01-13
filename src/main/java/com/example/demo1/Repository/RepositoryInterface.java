package com.example.demo1.Repository;


import com.example.demo1.Domain.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public interface RepositoryInterface<T extends Entity> extends Iterable<T> {
    ArrayList<T> getAll();
    void add(T Entity) throws IOException;
    T get(int poz);
    T getById(int id);
    int size();
    void delete(T Entity) throws IOException;

    void update(T Entity) throws IOException;
    Iterator<T> iterator();
}
