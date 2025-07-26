package com.IdeaProjects.store.repositories;

import com.IdeaProjects.store.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}