package com.yash.services.impl;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yash.exception.ResourceNotFoundException;
import com.yash.model.Category;
import com.yash.model.Post;
import com.yash.model.User;
import com.yash.payloads.PostDTO;
import com.yash.payloads.PostResponse;
import com.yash.repository.CategoryRepo;
import com.yash.repository.PostRepo;
import com.yash.repository.UserRepo;
import com.yash.services.PostService;

@Service
public class PostServiceImpl implements PostService
{
	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;
    
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	//create
	@Override
	public PostDTO createPost(PostDTO postDTO,Integer userId, Integer catId)
	{	
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		
		Category category = this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category", "category Id", catId));
		
		Post post = this.modelMapper.map(postDTO, Post.class);
		post.setUser(user);
		post.setCategory(category);
		post.setImgname("default.png");
		post.setAddeddate(new Date());
		
		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDTO.class);//
	}

	//update
	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) 
	{
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));		
		post.setContent(postDTO.getContent());
		post.setTitle(postDTO.getTitle());
		post.setImgname(postDTO.getImgName());
		
		Post save = this.postRepo.save(post);
		return this.modelMapper.map(save, PostDTO.class);
	}

	@Override
	public PostDTO getPost( Integer postId) 
	{
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		return this.modelMapper.map(post, PostDTO.class);
	}
   //get all
	@Override
	public  PostResponse getAllPost(Integer pageNo,Integer pageSize,String sortBy,String sortDir)
	{
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort=Sort.by(sortBy).ascending();
		}
		else
		{
			sort=Sort.by(sortBy).descending();
		}
		
		 Pageable p= PageRequest.of(pageNo, pageSize, sort);
		 
		 Page<Post> pagePost = this.postRepo.findAll(p);
		 List<Post> allposts = pagePost.getContent();
		
		List<PostDTO> postdtos = allposts.stream().map((all)-> this.modelMapper.map(all, PostDTO.class)).collect(Collectors.toList());
		
		 PostResponse postResponse = new PostResponse();
		 postResponse.setContent(postdtos);
		 postResponse.setPageNo(pagePost.getNumber());
		 postResponse.setPageSize(pagePost.getSize());
		 postResponse.setTotalElements(pagePost.getTotalElements());
		 postResponse.setTotalPages(pagePost.getTotalPages());
		 postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public void deletePost(Integer postId)
	{
		this.postRepo.deleteById(postId);
	}

	//get by user
	@Override
	public List<PostDTO> getPostByUser(Integer userId)
	{
		
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User ", "userId", userId));
		List<Post> usersPost = this.postRepo.findByUser(user);
		
		List<PostDTO> userDtos = usersPost.stream().map((upost) -> this.modelMapper.map(upost, PostDTO.class)).collect(Collectors.toList());
		return userDtos;
	}
	//post by category
	@Override
	public List<PostDTO> getPostByCategory(Integer catId) 
	{
		Category cat = this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category", "Cat id", catId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDTO> dtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return dtos;
		
	}
	//search post depth
	@Override
	public List<PostDTO> searchPosts(String keyword) 
	{
		List<Post> bytitle = this.postRepo.findBytitle("%"+keyword+"%");
		List<PostDTO> dtos = bytitle.stream().map((title) -> this.modelMapper.map(title, PostDTO.class)).collect(Collectors.toList());
		return dtos;
	}

}
