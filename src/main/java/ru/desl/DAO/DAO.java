package ru.desl.DAO;

import java.util.List;

public interface DAO<T> {

    void add(T item);

    void update(T item);

    void delete(T item);

    List<T> getAll();

    T getById(long id);

}
