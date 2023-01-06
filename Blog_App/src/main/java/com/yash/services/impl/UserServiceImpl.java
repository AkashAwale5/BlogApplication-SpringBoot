package com.yash.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.yash.model.User;
import com.yash.payloads.UserDTO;
import com.yash.repository.UserRepo;
import com.yash.services.UserService;
import com.yash.exception.*;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelmapper;
	
	@Override
	public UserDTO createUser(UserDTO userdto) 
	{
		User user = this.dtoToUser(userdto);
		User saveuser =this.userRepo.save(user);
		return	this.usertodto(saveuser);
		
	}

	@Override
	public UserDTO updateUser(UserDTO userdto, Integer userid)
	{
		User user =this.userRepo.findById(userid).orElseThrow((()-> new ResourceNotFoundException("User"," id ",userid)));
		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setPassword(userdto.getPassword());
		user.setAbout(userdto.getAbout());
		
		User update = this.userRepo.save(user);
		return this.usertodto(update);
		
	}

	@Override
	public UserDTO getUser(Integer userid) {

		User user =this.userRepo.findById(userid).orElseThrow((()-> new ResourceNotFoundException("User"," id ",userid)));
		
		return this.usertodto(user);
	}

	@Override
	public List<UserDTO> getAll() 
	{
		List<User> users = this.userRepo.findAll();
		 List<UserDTO> userlist = users.stream().map(user->this.usertodto(user)).collect(Collectors.toList());
		 return userlist;
	}

	@Override
	public void deleteuser(Integer userid)
	{
//		this.userRepo.deleteById(userid);
		User user =this.userRepo.findById(userid).orElseThrow((()-> new ResourceNotFoundException("User"," id ",userid)));
		this.userRepo.delete(user);
	}

	
	private User dtoToUser(UserDTO userdto)
	{
		User user = this.modelmapper.map(userdto, User.class);
//		user.setId(userdto.getId());
//		user.setName(userdto.getName());
//		user.setEmail(userdto.getEmail());
//		user.setPassword(userdto.getPassword());
//		user.setAbout(userdto.getAbout());
	
		return user;
	}
	
	private UserDTO usertodto(User user)
	{
		UserDTO userdto = this.modelmapper.map(user, UserDTO.class);
//		userdto.setId(user.getId());
//		userdto.setName(user.getName());
//		userdto.setEmail(user.getEmail());
//		userdto.setPassword(user.getPassword());
//		userdto.setAbout(user.getAbout());
	
		return userdto;
	}
}
