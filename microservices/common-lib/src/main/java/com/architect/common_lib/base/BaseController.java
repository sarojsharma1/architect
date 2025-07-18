package com.architect.common_lib.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping
public class BaseController<T, ID extends Serializable> {
    private final BaseMapper<T> baseMapper;
    private final BaseServiceImp<T> baseServiceImp;
    private final BaseRepository<T, ID> baseRepository;

    public BaseController(BaseMapper<T> baseMapper,
                          BaseServiceImp<T> baseServiceImp,
                          BaseRepository<T, ID> baseRepository) {
        this.baseMapper = baseMapper;
        this.baseServiceImp = baseServiceImp;
        this.baseRepository = baseRepository;
    }
}
