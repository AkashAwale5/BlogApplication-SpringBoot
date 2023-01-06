package com.yash.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.exception.ResourceNotFoundException;
import com.yash.model.Category;
import com.yash.payloads.CategoryDto;
import com.yash.repository.CategoryRepo;
import com.yash.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService
{

	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto)
	{
		Category cat = this.modelmapper.map(categoryDto, Category.class);
		Category savedcateCategory = this.categoryRepo.save(cat);
		return this.modelmapper.map(savedcateCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId)
	{
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ", "Category ID", categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setDescription(categoryDto.getDescription());
		
		Category updatedcat = this.categoryRepo.save(cat);
		
		return this.modelmapper.map(updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) 
	{
		// TODO Auto-generated method stub
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ", "Category ID", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getcateCategory(Integer categoryId) 
	{
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ", "Category ID", categoryId));
		
		return this.modelmapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAll() 
	{
		List<Category> findAll = this.categoryRepo.findAll();
		List<CategoryDto> list = findAll.stream().map((cat)->this.modelmapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		 return list;
	}

}
