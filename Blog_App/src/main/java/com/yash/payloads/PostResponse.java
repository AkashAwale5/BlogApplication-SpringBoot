package com.yash.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class PostResponse 
{
	private List<PostDTO> content;
	private int pageNo;
	private int pageSize;
	private int totalPages;
	private long totalElements;
	
	private boolean lastPage;
}
