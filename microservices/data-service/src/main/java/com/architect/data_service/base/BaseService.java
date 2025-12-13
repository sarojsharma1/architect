package com.architect.data_service.base;

import java.util.List;
import java.util.Optional;

public interface BaseService<E extends BaseEntity, ID> {
    E save(E t);

    E update(ID id, E t);

    void deleteById(ID id);

    Optional<E> findById(ID id);

    List<E> findAll();
}
