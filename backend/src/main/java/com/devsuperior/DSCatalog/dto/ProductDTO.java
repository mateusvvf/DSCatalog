package com.devsuperior.DSCatalog.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.devsuperior.DSCatalog.entities.Product;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {
	
	private Long id;
	
	@NotBlank(message = "This field cannot be blank.")
	@Size(min = 5, max = 60, message = "Must have between 5 and 60 characters.")
	private String name;
	
	@NotBlank(message = "This field cannot be blank.")
	@Size(max = 600, message = "Description must not exceed 600 characters.")
	private String description;
	
	@Digits(integer = 10, fraction = 2, message = "The price must have up to 2 decimal places.")
	@Positive(message = "The price must be a positive number.")
	private Double price;
	
	@Pattern(regexp = "https?:\\/\\/.+\\.(?:png|jpg|jpeg)$",
			message = "Please enter a valid image URL.")
	private String imgUrl;
	
	@PastOrPresent(message = "Product date cannot be future.")
	private Instant date;
	
	private List<CategoryDTO> categories = new ArrayList<>();

	public ProductDTO() {
		
	}

	public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.date = date;
	}
	
	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
		date = entity.getDate();
		
		entity.getCategories().forEach(cat -> categories.add(new CategoryDTO(cat)));		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
	
}
