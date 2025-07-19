package com.architect.common_lib.base;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, ID> {
    T save(T entity);

    T update(ID id, T entity);

    void deleteById(ID id);

    Optional<T> findById(ID id);

    List<T> findAll();
}
