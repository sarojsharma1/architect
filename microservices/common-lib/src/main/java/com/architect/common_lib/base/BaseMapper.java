package com.architect.common_lib.base;

import org.mapstruct.Mapper;

import java.io.Serializable;

@Mapper(componentModel = "spring")
public interface BaseMapper<T extends Serializable> {
    BaseDto<T> toDto(BaseEntity<T> entity);

    BaseEntity<T> toEntity(BaseDto<T> dto);
}
