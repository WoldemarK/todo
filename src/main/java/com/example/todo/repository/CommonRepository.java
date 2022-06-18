package com.example.todo.repository;

import java.util.Collection;

public interface CommonRepository<T> {

    public T save(T model);

    public Iterable<T> save(Collection<T> model);

    public void delete(T model);

    public T findById(String id);

    public Iterable<T> findAll();
}
