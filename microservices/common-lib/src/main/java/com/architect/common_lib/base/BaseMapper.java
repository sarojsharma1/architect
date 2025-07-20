package com.architect.common_lib.base;

import org.mapstruct.MapperConfig;

@MapperConfig(componentModel = "spring")
public interface BaseMapper<E extends BaseEntity, D extends BaseDto> {
    D toDto(E entity);

    E toEntity(D dto);
}
