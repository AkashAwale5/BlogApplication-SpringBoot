package com.yash.services;

import com.yash.payloads.CommentDTO;

public interface CoomentService 
{
	public CommentDTO createComment(CommentDTO commentDTO,Integer postID );
	void deleteComment(Integer commentID);
}
