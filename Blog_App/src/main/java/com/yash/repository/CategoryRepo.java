package com.yash.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
