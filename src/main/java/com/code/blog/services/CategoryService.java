package com.code.blog.services;

import java.util.List;

import com.code.blog.payload.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto,Integer catID);
	void deleteCategory(Integer catID);
	CategoryDto getCategory(Integer catID);
	List<CategoryDto> getCategories();
}
