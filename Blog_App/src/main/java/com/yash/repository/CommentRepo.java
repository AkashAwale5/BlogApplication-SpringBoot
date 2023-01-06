package com.yash.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.model.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
