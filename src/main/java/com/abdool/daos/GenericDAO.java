package com.abdool.daos;

//// List <E> -> List<Scores> - only Scores can be stored


import java.util.List;

public interface GenericDAO<T> {

    //Create
    T add(T t);

    //Read
    T getById(int id);

    List<T> getAll();

    //Update
    void update(T t);

    //Delete
    void delete(int id);

}
