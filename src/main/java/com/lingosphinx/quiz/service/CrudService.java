package com.lingosphinx.quiz.service;

import java.util.List;

public interface CrudService<T> {
    T create(T journey);

    T readById(Long id);
    List<T> readAll();
    T update(Long id, T journey);
    void delete(Long id);
}
