package com.example.contact.models.mappers;

import java.util.List;

public interface MapperDto<DTO,E, RESP> {
    RESP mapToDto(E entity);
    E mapToEntities(DTO dto);
    List<RESP> maptoDtoList(Iterable<E> entities);
    List<E> mapToEntities(Iterable<DTO> dtos);
}
