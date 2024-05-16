package com.example.prueba_apod.models;

public class APODkey {
    String key;
    int id;

    public APODkey() {
    }

    public APODkey(String key, int id) {
        this.key = key;
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
