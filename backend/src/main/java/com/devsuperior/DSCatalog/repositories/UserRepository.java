package com.devsuperior.DSCatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.DSCatalog.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	
}
