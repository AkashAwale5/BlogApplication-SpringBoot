package com.yash.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.payloads.ApiResponse;
import com.yash.payloads.UserDTO;
import com.yash.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserControllers 
{
	@Autowired
	private UserService userService;
	
	//post -create user
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userdto)
	{
		UserDTO createUserdto = this.userService.createUser(userdto);
		return new ResponseEntity<>(createUserdto,HttpStatus.CREATED);
	}
	//put - update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUserById(@Valid @RequestBody UserDTO userdto,@PathVariable("userId") Integer userId)
	{
		UserDTO updatedUser = this.userService.updateUser(userdto, userId);
		return ResponseEntity.ok(updatedUser);
	}
	//delete - delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUserById(@PathVariable("userId") Integer userId)
	{
		userService.deleteuser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
	}
	//get
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAlluser()
	{
		
		return ResponseEntity.ok(this.userService.getAll());
	}
	@GetMapping("/{userid}")
	public ResponseEntity<UserDTO> getSingleuser(@PathVariable Integer userid)
	{
		
		return ResponseEntity.ok(this.userService.getUser(userid));
	}

}
