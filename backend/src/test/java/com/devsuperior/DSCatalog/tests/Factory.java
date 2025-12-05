package com.devsuperior.DSCatalog.tests;

import java.time.Instant;

import com.devsuperior.DSCatalog.dto.ProductDTO;
import com.devsuperior.DSCatalog.entities.Category;
import com.devsuperior.DSCatalog.entities.Product;

public class Factory {

	public static Product createProduct() {
		Product product = new Product(1L, "Book", "Book X", 800.0, "https://img.com/img.png", Instant.parse("2025-12-03T12:16:00Z"));
		product.getCategories().add(new Category(1L, "Electronics"));
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product);
	}
	
}
