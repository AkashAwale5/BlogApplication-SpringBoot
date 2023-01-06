package com.yash.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.exception.ResourceNotFoundException;
import com.yash.model.Comment;
import com.yash.model.Post;
import com.yash.payloads.CommentDTO;
import com.yash.repository.CommentRepo;
import com.yash.repository.PostRepo;
import com.yash.services.CoomentService;
@Service
public class CommentServiceImpl implements CoomentService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	private Comment save;
	
	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Integer postID)
	{
		Post post = this.postRepo.findById(postID).orElseThrow(()-> new ResourceNotFoundException("not found", "postID ", postID));
		Comment comment = this.modelMapper.map(commentDTO, Comment.class);
		comment.setPost(post);
		
		Comment savedcomment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedcomment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentID)
	{
		Comment comment = this.commentRepo.findById(commentID).orElseThrow(()->new ResourceNotFoundException("comment", "commentID", commentID));
		this.commentRepo.delete(comment);
	}

}
