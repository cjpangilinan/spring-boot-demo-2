package com.example.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "appusers", collectionResourceRel = "appusers")
public interface AppUserRepository extends CrudRepository<AppUser, Long> {

	List<AppUser> findByName(String name);
}
