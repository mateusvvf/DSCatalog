package com.devsuperior.DSCatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.DSCatalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
