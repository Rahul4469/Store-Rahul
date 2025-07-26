package com.IdeaProjects.store.mappers;

import com.IdeaProjects.store.dtos.CartDto;
import com.IdeaProjects.store.entities.Carts;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toDto(Carts cart);
}
