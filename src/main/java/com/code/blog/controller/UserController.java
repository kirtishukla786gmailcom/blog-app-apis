package com.code.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.blog.payload.ApiResponse;
import com.code.blog.payload.UserDto;
import com.code.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	public ResponseEntity<UserDto> responseEntity;
	//create user
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createdUser=	this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
		
	}
	//update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uId){
		
		UserDto updatedUser=	this.userService.updateUser(userDto, uId);
		return ResponseEntity.ok(updatedUser);
		
	}
	
	//delete user
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uId){
		
            this.userService.deleteUser(uId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully",true),HttpStatus.OK);
		
	}
	
	//get all user
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser(){
		
		return ResponseEntity.ok(this.userService.getAllUser());
		
	}
	
	//get single user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer uId){
		
		return ResponseEntity.ok(this.userService.getUserByUserId(uId));
		
	}
}
