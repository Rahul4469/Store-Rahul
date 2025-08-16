package com.IdeaProjects.store.service;

import com.IdeaProjects.store.dtos.CartDto;
import com.IdeaProjects.store.dtos.CartItemDto;
import com.IdeaProjects.store.entities.Carts;
import com.IdeaProjects.store.exceptions.CartNotFoundException;
import com.IdeaProjects.store.exceptions.ProductNotFoundException;
import com.IdeaProjects.store.mappers.CartMapper;
import com.IdeaProjects.store.repositories.CartsRepository;
import com.IdeaProjects.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private CartsRepository cartsRepository;
    private CartMapper cartMapper;
    private ProductRepository productRepository;

    public CartDto createCart(){
        var cart = new Carts();
        cartsRepository.save(cart);

        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId){
        var cart = cartsRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        var product = productRepository.findById(productId).orElse(null);
        if(product == null){
            throw new ProductNotFoundException();
        }

//        var cartItem = cart.getCartItems().stream()
//               .filter(item -> item.getProduct().getId().equals(product.getId()))
//                .findFirst()
//                .orElse(null);
        //****** Created this method in the Carts class
//        var cartItem = cart.getItem(product.getId());
//        if (cartItem != null){
//            cartItem.setQuantity(cartItem.getQuantity() + 1);
//        }
//        else{
//            cartItem = new CartItems();
//            cartItem.setProduct(product);
//            cartItem.setQuantity(1);
//            cartItem.setCarts(cart);
//            cart.getCartItems().add(cartItem);
//        }
        var cartItem =  cart.addItem(product);
        cartsRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId){
        var cart = cartsRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        return cartMapper.toDto(cart);
    }

    public CartItemDto updateItem(UUID cartId, Long productId, Integer quantity){
        var cart = cartsRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            throw new  CartNotFoundException();
        }

//        var cartItem = cart.getCartItems().stream()
//               .filter(item -> item.getProduct().getId().equals(productId)
//                .findFirst()
//                .orElse(null);
        //Created this method in the Carts class
        var cartItem = cart.getItems(productId);
        if(cartItem == null){
            throw new ProductNotFoundException();
        }

        cartItem.setQuantity(quantity);
        cartsRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public void removeItem(UUID cartId, Long productId){
        var cart = cartsRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

//        var cartItem =  cart.getItems(productId);
//        if(cartItem != null) {
//            cart.getItems().remove(cartItem);
//        }
        cart.removeItem(productId);

        cartsRepository.save(cart);
    }

    public void clearCart(UUID cartId){
        var cart = cartsRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        cart.clear();
        cartsRepository.save(cart);
    }
}
