package com.architect.data_service.base;

import org.mapstruct.MapperConfig;

import java.util.List;

@MapperConfig(componentModel = "spring")
public interface BaseMapper<E extends BaseEntity, D extends BaseDto> {
    D toDto(E entity);

    List<D> toDto(List<E> entity);

    E toEntity(D dto);
}
