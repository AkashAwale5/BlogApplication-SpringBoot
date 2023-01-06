package com.yash.services;

import java.util.List;
import com.yash.payloads.PostDTO;
import com.yash.payloads.PostResponse;

public interface PostService 
{
	
	//create
	
	PostDTO createPost(PostDTO postDTO,Integer userId, Integer catId);
	
	//update
	
	PostDTO updatePost(PostDTO postDTO, Integer postId);
	
	//get single Post
	
	PostDTO getPost( Integer postId);
	//All post
    
    PostResponse getAllPost(Integer pageNo,Integer pageSize,String sortBy,String sortDir);
	//delete
	
	 void deletePost(Integer postId);
	//post by user 
	List<PostDTO> getPostByUser(Integer userId);
	
	//by cat
	List<PostDTO> getPostByCategory(Integer catId);
	
	//search post
	List<PostDTO> searchPosts(String keyword);
	
	//pagination 
	
	
}
