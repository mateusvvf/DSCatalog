package com.devsuperior.DSCatalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.DSCatalog.dto.CategoryDTO;
import com.devsuperior.DSCatalog.entities.Category;
import com.devsuperior.DSCatalog.repositories.CategoryRepository;
import com.devsuperior.DSCatalog.services.exceptions.ResourceNotFoundException;


@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> categories = repository.findAll();
		return categories.stream().map(obj -> new CategoryDTO(obj)).toList();
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> result = repository.findById(id);
		Category category = result.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
		return new CategoryDTO(category);
	}
	
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

}
