package com.architect.common_lib.base;

import com.architect.common_lib.exception.EntityNotFoundException;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


public abstract class BaseServiceImp<T, ID extends Serializable> implements BaseService<T, ID> {

    private final BaseRepository<T, ID> baseRepository;

    protected BaseServiceImp(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public T save(T entity) {
        return baseRepository.save(entity);
    }

    @Override
    public T update(ID id, T entity) {
        if (!baseRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity with ID" + id + "not found");
        }
        return baseRepository.save(entity);
    }

    @Override
    public void deleteById(ID id) {
        baseRepository.deleteById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return baseRepository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }
}
