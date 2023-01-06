package com.yash.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.payloads.ApiResponse;
import com.yash.payloads.CategoryDto;
import com.yash.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController
{
	
	@Autowired
	CategoryService categoryService;
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createcategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto createCategory = categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createCategory,HttpStatus.CREATED);
		
	}
	
	//update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updatecategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId)
	{
		CategoryDto updatedCat = categoryService.updateCategory(categoryDto, catId);
		return ResponseEntity.ok(updatedCat);
	}
	
	//get single
	@GetMapping("/{catid}")
	public ResponseEntity<CategoryDto> getSingle(@PathVariable Integer catid)
	{
		CategoryDto getcat = categoryService.getcateCategory(catid);
		return ResponseEntity.ok(getcat);
	}
	
	//get alll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAll()
	{
		
		return ResponseEntity.ok(categoryService.getAll());
	}
	
	//delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deletecat(@PathVariable Integer catId)
	{
		categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted successfully",true),HttpStatus.OK);
	}
	
}
