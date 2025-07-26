package com.IdeaProjects.store.mappers;

import com.IdeaProjects.store.dtos.ProductDto;
import com.IdeaProjects.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    //method that returns ProductDto
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toDto(Product product);
}
