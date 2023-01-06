package com.yash.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO 
{
	private int id;
	
	@NotEmpty
	@Size(min=4, message="size cannot be less than 4")
	private String name;
	
	@Email(message="Email address is not valid ")
	private String email;
	
	@NotEmpty(message="Password Must not empty")
	private String password;
	@NotEmpty(message="about Must not empty")
	private String about;
}
