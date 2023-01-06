package com.yash.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yash.payloads.UserDTO;


public interface UserService 
{
	 UserDTO createUser(UserDTO user);
	 UserDTO updateUser(UserDTO user,Integer userid);
	 UserDTO getUser(Integer userid);
	 
	 List<UserDTO> getAll();
	 void deleteuser(Integer userid);
}
