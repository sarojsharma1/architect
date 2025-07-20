package com.architect.common_lib.base;

import com.architect.common_lib.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public abstract class BaseController<T extends BaseEntity, ID, D extends BaseDto> {
    private final BaseServiceImp<T, ID> baseService;
    private final BaseMapper<T, D> baseMapper;

    protected BaseController(BaseServiceImp<T, ID> baseService,
                             BaseMapper<T, D> baseMapper) {
        this.baseService = baseService;
        this.baseMapper = baseMapper;
    }

    @PostMapping()
    public ResponseEntity<ResponseDto> save(@RequestBody D requestDto) {
        T entity = baseMapper.toEntity(requestDto);
        T savedEntity = baseService.save(entity);
        D responseDto = baseMapper.toDto(savedEntity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("Success", responseDto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseDto> update(@PathVariable ID id,
                                              @RequestBody D requestDto) {
        T entity = baseMapper.toEntity(requestDto);
        T updatedEntity = baseService.update(id, entity);
        D responseDto = baseMapper.toDto(updatedEntity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("Success", responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable ID id) {
        baseService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("Success", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(@PathVariable ID id) {
        Optional<T> entity = baseService.findById(id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("Success", null));
    }

    @GetMapping()
    public ResponseEntity<ResponseDto> findAll() {
        List<T> entities = baseService.findAll();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("Success", null));
    }
}
