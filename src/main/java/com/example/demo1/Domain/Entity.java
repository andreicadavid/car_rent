package com.example.demo1.Domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private static final long serialVersionUID = 100L;
    protected int id;

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
