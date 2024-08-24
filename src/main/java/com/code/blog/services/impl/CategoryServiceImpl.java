package com.code.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code.blog.entities.Category;
import com.code.blog.exceptions.ResourceNotFoundException;
import com.code.blog.payload.CategoryDto;
import com.code.blog.repositories.CategoryRepo;
import com.code.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category savedCategory=this.categoryRepo.save(modelMapper.map(categoryDto, Category.class));
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer catID) {
	
		// TODO Auto-generated method stub
		Category cat=this.categoryRepo.findById(catID).orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", catID) );
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		return modelMapper.map(this.categoryRepo.save(cat), CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer catID) {
		Category cat=this.categoryRepo.findById(catID).orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", catID) );
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer catID) {
		Category cat=this.categoryRepo.findById(catID).orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", catID) );
		return modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		// TODO Auto-generated method stub
		List<Category> cat=this.categoryRepo.findAll();
		return cat.stream().map(c -> this.modelMapper.map(c, CategoryDto.class)).collect(Collectors.toList());
	}

}
