package com.yash.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.model.User;

public interface UserRepo extends JpaRepository<User,Integer> 
{
	Optional<User> findByEmail(String email);
}
