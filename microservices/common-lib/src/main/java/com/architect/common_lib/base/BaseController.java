package com.architect.common_lib.base;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

public abstract class BaseController<T, ID extends Serializable> {
    private final BaseServiceImp<T, ID> baseService;

    protected BaseController(BaseServiceImp<T, ID> baseService) {
        this.baseService = baseService;
    }

    @PostMapping()
    public void save(@RequestBody T dto) {
        T entity = baseService.save(dto);
    }

    @PostMapping()
    public void update(@PathVariable ID id,
                       @RequestBody T dto) {
        baseService.update(id, dto);
    }

    @GetMapping()
    public void deleteById(@PathVariable ID id) {
        baseService.deleteById(id);
    }

    @GetMapping()
    public void findById(@PathVariable ID id) {
        baseService.findById(id);
    }

    @GetMapping()
    public void findAll() {
        baseService.findAll();
    }
}
