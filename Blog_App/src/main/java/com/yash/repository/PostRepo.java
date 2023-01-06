package com.yash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yash.model.Category;
import com.yash.model.Post;
import com.yash.model.User;

public interface PostRepo extends JpaRepository<Post, Integer>
{
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	@Query("select p from Post p where p.title like :key")
	List<Post> findBytitle(@Param ("key") String title);
}
