package com.courseproject.model;

// базовый класс для всех можелй
public class BaseModel {
    protected long id;

    public BaseModel(long id) {
        this.id = id;
    }

    public BaseModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

