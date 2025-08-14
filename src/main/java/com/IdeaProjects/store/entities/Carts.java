package com.IdeaProjects.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "carts", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CartItems> cartItems = new LinkedHashSet<>();

    public BigDecimal getTotalPrice(){
        return cartItems.stream()
                .map(CartItems::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public CartItems getItems(Long productId){
        return cartItems.stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public CartItems addItem(Product product){
        var cartItem = getItems(product.getId());
        if (cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }
        else{
            cartItem = new CartItems();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCarts(this);
            cartItems.add(cartItem);
        }
        return cartItem;
    }

    public void removeItem(Long productId){
        var cartItem = getItems(productId);
        if (cartItem != null){
           cartItems.remove(cartItem);
           cartItem.setCarts(null);
        }
    }

    public void clear(){
        cartItems.clear();
    }
}
