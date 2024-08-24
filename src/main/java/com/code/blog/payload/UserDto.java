package com.code.blog.payload;

import java.util.HashSet;
import java.util.Set;

import com.code.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	@NotEmpty
	@Size(min =4 , message = "user name must be minimum of 4 charcter !!!")
	private String name;
	@Email(message = "email message is not valid !!!!!")
	private String email;
	@NotEmpty
	@Size(min =4 ,max =15, message = "passowrd must be between 4 to 10 char !!!")
	
	private String password;
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles = new HashSet<RoleDto>();
}
