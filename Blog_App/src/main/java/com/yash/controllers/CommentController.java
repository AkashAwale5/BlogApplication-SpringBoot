package com.yash.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.payloads.ApiResponse;
import com.yash.payloads.CommentDTO;
import com.yash.services.CoomentService;

@RestController
@RequestMapping("/api/")
public class CommentController 
{

	@Autowired
	private CoomentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO,@PathVariable Integer postId)
	{
		CommentDTO createComment = this.commentService.createComment(commentDTO, postId);
		return new ResponseEntity<CommentDTO>(createComment,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
	{
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted",true),HttpStatus.OK);
		
	}
}
