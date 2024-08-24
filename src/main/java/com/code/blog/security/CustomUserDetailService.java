package com.code.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.code.blog.exceptions.ResourceNotFoundException;
import com.code.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{
 
	@Autowired
	 private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//loading  user by user name from db
	return userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User"," Email :"+ username, 0));
	}

}
