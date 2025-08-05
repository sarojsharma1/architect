package com.architect.common_lib.base;

import com.architect.common_lib.exception.NotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public abstract class BaseServiceImp<E extends BaseEntity, ID> implements BaseService<E, ID> {

    private final BaseRepository<E, ID> baseRepository;

    protected BaseServiceImp(BaseRepository<E, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public E save(E entity) {
        return baseRepository.save(entity);
    }

    @Override
    public E update(ID id, E t) {
        if (!baseRepository.existsById(id)) {
            throw new NotFoundException("Entity with ID" + id + "not found");
        }
        return baseRepository.save(t);
    }

    @Override
    public void deleteById(ID id) {
        baseRepository.deleteById(id);
    }

    @Override
    public Optional<E> findById(ID id) {
        return baseRepository.findById(id);
    }

    @Override
    public List<E> findAll() {
        return baseRepository.findAll();
    }
}
