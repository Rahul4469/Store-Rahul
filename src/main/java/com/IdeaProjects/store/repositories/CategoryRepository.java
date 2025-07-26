package com.IdeaProjects.store.repositories;

import com.IdeaProjects.store.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}