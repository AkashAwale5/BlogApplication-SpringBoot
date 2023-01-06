package com.yash.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.yash.model.Category;
import com.yash.model.Comment;
import com.yash.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO 
{
	private String title;
	
	private String content;
	
	private String imgName;
	
	private Date addeddate;
	
	private CategoryDto category;
	
	private UserDTO user;
	
	private Set<CommentDTO> comments= new HashSet<>();
	
}
