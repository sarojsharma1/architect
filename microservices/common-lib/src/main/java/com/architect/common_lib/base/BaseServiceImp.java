package com.architect.common_lib.base;

import com.architect.common_lib.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public abstract class BaseServiceImp<T extends BaseEntity, ID> implements BaseService<T, ID> {

    private final BaseRepository<T, ID> baseRepository;

    protected BaseServiceImp(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public T save(T entity) {
        return baseRepository.save(entity);
    }

    @Override
    public T update(ID id, T t) {
        if (!baseRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity with ID" + id + "not found");
        }
        return baseRepository.save(t);
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
