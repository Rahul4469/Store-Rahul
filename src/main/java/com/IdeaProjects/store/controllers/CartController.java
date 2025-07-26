package com.IdeaProjects.store.controllers;

import com.IdeaProjects.store.dtos.CartDto;
import com.IdeaProjects.store.entities.Carts;
import com.IdeaProjects.store.mappers.CartMapper;
import com.IdeaProjects.store.repositories.CartsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartsRepository cartsRepository;
    private final CartMapper cartMapper;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ){
        var cart = new Carts();
        cartsRepository.save(cart);

        var cartDto = cartMapper.toDto(cart);
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }
}
