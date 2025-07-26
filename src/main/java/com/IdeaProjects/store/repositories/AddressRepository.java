package com.IdeaProjects.store.repositories;

import com.IdeaProjects.store.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}