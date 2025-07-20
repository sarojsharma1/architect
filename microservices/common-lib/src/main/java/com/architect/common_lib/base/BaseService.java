package com.architect.common_lib.base;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity, ID> {
    T save(T t);

    T update(ID id, T t);

    void deleteById(ID id);

    Optional<T> findById(ID id);

    List<T> findAll();
}
