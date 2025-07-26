package com.IdeaProjects.store.repositories;

import com.IdeaProjects.store.entities.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartsRepository extends JpaRepository<Carts, Long> {
}