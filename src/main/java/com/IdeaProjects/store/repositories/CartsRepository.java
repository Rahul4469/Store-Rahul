package com.IdeaProjects.store.repositories;

import com.IdeaProjects.store.entities.Carts;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartsRepository extends JpaRepository<Carts, UUID> {
    @EntityGraph(attributePaths = {"cartItems.product"})
    @Query("SELECT c FROM Carts c  WHERE c.id = :cartId")
    Optional<Carts> getCartWithItems(@Param("cartId") UUID cartId);

}