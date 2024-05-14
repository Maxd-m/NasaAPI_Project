package com.example.prueba_apod.database.dao;

import com.example.prueba_apod.models.User;

import java.util.List;
import java.util.Optional;

public interface Dao<T,ID> {
    Optional<T> findById(ID id);

    List<T> findAll();

    boolean delete(Object o);

    boolean update(Object record);

    boolean save(Object record);

    boolean save(Object record, int id);
}
