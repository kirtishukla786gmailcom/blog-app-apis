package com.code.blog.services;

import java.util.List;

import com.code.blog.payload.UserDto;

public interface UserService {
  
	UserDto registerNewUser(UserDto user);
	UserDto createUser(UserDto user);
	UserDto  updateUser(UserDto user,Integer userID);
	UserDto getUserByUserId(Integer userID);
	List<UserDto> getAllUser();
	void deleteUser(Integer userID);
}
