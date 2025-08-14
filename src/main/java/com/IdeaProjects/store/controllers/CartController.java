package com.IdeaProjects.store.controllers;

import com.IdeaProjects.store.dtos.AddItemToCartRequest;
import com.IdeaProjects.store.dtos.CartDto;
import com.IdeaProjects.store.dtos.CartItemDto;
import com.IdeaProjects.store.dtos.UpdateCartItemRequest;
import com.IdeaProjects.store.entities.Carts;
import com.IdeaProjects.store.mappers.CartMapper;
import com.IdeaProjects.store.repositories.CartsRepository;
import com.IdeaProjects.store.repositories.ProductRepository;
import com.IdeaProjects.store.service.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartsRepository cartsRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ){
//        var cart = new Carts();
//        cartsRepository.save(cart);
//
//        var cartDto = cartMapper.toDto(cart);
        //***code belongs in Service layer
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(
            @PathVariable UUID cartId,
            @RequestBody AddItemToCartRequest request){
        var cartItemDto = cartService.addToCart(cartId, request.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId){
        var cart = cartsRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cartMapper.toDto(cart));
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId,
            @Valid @RequestBody UpdateCartItemRequest request){
        var cart = cartsRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Cart not found")
            );
        }

//        var cartItem = cart.getCartItems().stream()
//               .filter(item -> item.getProduct().getId().equals(productId)
//                .findFirst()
//                .orElse(null);
        //Created this method in the Carts class
        var cartItem = cart.getItems(productId);
        if(cartItem == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Cart item not found")
            );
        }

        cartItem.setQuantity(request.getQuantity());
        cartsRepository.save(cart);

        return ResponseEntity.ok(cartMapper.toDto(cartItem));
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> removeItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId
    ){
        var cart = cartsRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Cart was not found")
            );
        }

//        var cartItem =  cart.getItems(productId);
//        if(cartItem != null) {
//            cart.getItems().remove(cartItem);
//        }
        cart.removeItem(productId);

        cartsRepository.save(cart);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cartId){
        var cart = cartsRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        cart.clear();
        cartsRepository.save(cart);

        return ResponseEntity.noContent().build();
    }

    //---Exception handling logic

//    public ResponseEntity<Map<String, String>> handleCrtNotFound(){
//    }

}
