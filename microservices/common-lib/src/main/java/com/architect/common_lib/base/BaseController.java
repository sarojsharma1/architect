package com.architect.common_lib.base;

import com.architect.common_lib.dto.ResponseDto;
import com.architect.common_lib.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public abstract class BaseController<ID, E extends BaseEntity, D extends BaseDto> {
    private final BaseServiceImp<E, ID> baseService;
    private final BaseMapper<E, D> baseMapper;

    protected BaseController(BaseServiceImp<E, ID> baseService,
                             BaseMapper<E, D> baseMapper) {
        this.baseService = baseService;
        this.baseMapper = baseMapper;
    }

    @PostMapping()
    public ResponseEntity<ResponseDto> save(@RequestBody D requestDto) {
        E entity = baseMapper.toEntity(requestDto);
        E savedEntity = baseService.save(entity);
        D responseDto = baseMapper.toDto(savedEntity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("Success", responseDto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseDto> update(@PathVariable ID id,
                                              @RequestBody D requestDto) {
        E entity = baseMapper.toEntity(requestDto);
        E updatedEntity = baseService.update(id, entity);
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
        Optional<E> OptEntity = baseService.findById(id);
        E entity = OptEntity.orElseThrow(() -> new NotFoundException("Entity not found"));
        D responseDto = baseMapper.toDto(entity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("Success", responseDto));
    }

    @GetMapping()
    public ResponseEntity<ResponseDto> findAll() {
        List<E> entities = baseService.findAll();
        List<D> responseDto = baseMapper.toDto(entities);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("Success", responseDto));
    }
}
