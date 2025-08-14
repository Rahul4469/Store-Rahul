package com.IdeaProjects.store.mappers;

import com.IdeaProjects.store.dtos.CartDto;
import com.IdeaProjects.store.dtos.CartItemDto;
import com.IdeaProjects.store.entities.CartItems;
import com.IdeaProjects.store.entities.Carts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.thymeleaf.context.IExpressionContext;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "items", source = "cartItems")
    @Mapping(target = "totalPrice" , expression = "java(cart.getTotalPrice())")
    CartDto toDto(Carts cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItems cartItem);
}
