package com.code.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.code.blog.config.AppConstants;
import com.code.blog.entities.Role;
import com.code.blog.entities.User;
import com.code.blog.exceptions.ResourceNotFoundException;
import com.code.blog.payload.UserDto;
import com.code.blog.repositories.RoleRepo;
import com.code.blog.repositories.UserRepo;
import com.code.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo  roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
	    
		User user=modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			
		Role role=this.roleRepo.findById(AppConstants.ADMIN_USER).get();
		user.getRoles()	.add(role);
		return modelMapper.map(userRepo.save(user), UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
	User user=this.userRepo.findById(userId).
				orElseThrow(() -> new ResourceNotFoundException("User","id ",userId));
	user.setAbout(userDto.getAbout());
	user.setName(userDto.getName());
	user.setEmail(userDto.getEmail());
	user.setPassword(userDto.getPassword());
	return this.userToUserDto(this.userRepo.save(user));
	}

	@Override
	public UserDto getUserByUserId(Integer userID) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userID).
				orElseThrow(() -> new ResourceNotFoundException("User","id ",userID));
	
		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		// TODO Auto-generated method stub
		List<User>	users=this.userRepo.findAll();
		return users.stream().map(u -> this.userToUserDto(u)).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Integer userID) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userID).
				orElseThrow(() -> new ResourceNotFoundException("User","id ",userID));
		this.userRepo.delete(user);
		
	}
	private User userDtoToUser(UserDto dto) {
		User user=this.modelMapper.map(dto, User.class);
		return user;
		
		
	}
	private UserDto userToUserDto(User user) {
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		return userDto;
		
		
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user=modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles()	.add(role);
		return modelMapper.map(userRepo.save(user), UserDto.class);
	}
}
