package com.yash.services;

import java.util.List;

import com.yash.payloads.CategoryDto;

public interface CategoryService 
{
	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	//update
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId );
	//delete
	void deleteCategory(Integer categoryId);
	//get
	CategoryDto getcateCategory(Integer categoryId);
	//getall
	List<CategoryDto> getAll();
}
