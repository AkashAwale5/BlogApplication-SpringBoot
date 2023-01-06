package com.yash.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yash.payloads.ApiResponse;
import com.yash.payloads.PostDTO;
import com.yash.payloads.PostResponse;
import com.yash.services.FileService;
import com.yash.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController
{
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	//create
	@PostMapping("/user/{userId}/category/{catId}/posts")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO,@PathVariable Integer userId ,@PathVariable Integer catId)
	{
		 PostDTO createPost = this.postService.createPost(postDTO, userId, catId);
		 return new ResponseEntity<>(createPost,HttpStatus.CREATED);
	}
	//update
	@PutMapping("/update/{postid}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postdto,@PathVariable Integer postid)
	{
		PostDTO updatePost = this.postService.updatePost(postdto, postid);
		return  ResponseEntity.ok(updatePost);
	}
	//get by category
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<List<PostDTO>> getPostbyCat(@PathVariable Integer catId)
	{
		List<PostDTO> postByCategory = this.postService.getPostByCategory(catId);
		return new ResponseEntity<>(postByCategory,HttpStatus.ACCEPTED);
	}
	//getby user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostbyUser(@PathVariable Integer userId)
	{
		List<PostDTO> postByuser = this.postService.getPostByUser(userId);
		return new ResponseEntity<>(postByuser,HttpStatus.OK);
	}
	//get single
	@GetMapping("/get/{postId}")
	public ResponseEntity<PostDTO> getSinglePost(@PathVariable Integer postId)
	{
		PostDTO post = this.postService.getPost(postId);
		return new ResponseEntity<>(post,HttpStatus.OK);
		
	}
	//getAll
	@GetMapping("/allposts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam (value ="pageNo" ,defaultValue = "0", required = false) Integer pageNo,
			@RequestParam (value ="pageSize" ,defaultValue = "10", required = false) Integer pageSize,
			@RequestParam (value ="sortBy" ,defaultValue = "postid", required = false) String sortBy,
			@RequestParam (value ="sortDir" ,defaultValue = "asc", required = false) String sortDir
			)
	{
		  PostResponse allPost = this.postService.getAllPost(pageNo, pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
	}
	//delete post
	@DeleteMapping("/delete/{pid}")
	public ResponseEntity<ApiResponse> deletepost(@PathVariable Integer pid)
	{
		this.postService.deletePost(pid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successfully",true),HttpStatus.OK);

	}
	
	//search by post
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDTO>> searchPost(@PathVariable String keyword)
	{
		List<PostDTO> searchPosts = this.postService.searchPosts(keyword);
		return new ResponseEntity<>(searchPosts,HttpStatus.OK);	
	}
	
	//post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException
	{
		PostDTO postdto = this.postService.getPost(postId);
		
		String filename = this.fileService.uploadImage(path, image);
		postdto.setImgName(filename);
		PostDTO updatePost = this.postService.updatePost(postdto, postId);
		return new ResponseEntity<PostDTO>(updatePost,HttpStatus.OK);
		
	}
	//method to serve imAGE
	
	@GetMapping(value = "/post/image/{imagename}",produces = MediaType.IMAGE_JPEG_VALUE)
	public  void downloadImage(@PathVariable("imagename") String img,HttpServletResponse response) throws IOException
	{
		InputStream resoursce = this.fileService.getResoursce(path, img);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resoursce,response.getOutputStream());
	}
}
