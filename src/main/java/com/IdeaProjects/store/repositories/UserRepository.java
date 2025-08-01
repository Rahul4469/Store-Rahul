package com.IdeaProjects.store.repositories;

import com.IdeaProjects.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
