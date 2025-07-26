package com.IdeaProjects.store.repositories;

import com.IdeaProjects.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Long id(Long id);
}