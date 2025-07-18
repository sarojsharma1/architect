package com.architect.common_lib.base;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController<T, U, V> {
    //repository
    //mapper
    private final BaseServiceImp<U> baseServiceImp;

    public BaseController(BaseServiceImp<U> baseServiceImp) {
        this.baseServiceImp = baseServiceImp;
    }
}
