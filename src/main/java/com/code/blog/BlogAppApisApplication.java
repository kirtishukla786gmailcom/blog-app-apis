package com.code.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.code.blog.config.AppConstants;
import com.code.blog.controller.CategoryController;
import com.code.blog.entities.Role;
import com.code.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder; 
	@Autowired
	private RoleRepo  roleRepo;
	public static void main(String[] args) {
		/* ConfigurableApplicationContext configurableApplicationContext= */SpringApplication.run(BlogAppApisApplication.class, args);
		/*
		 * CategoryController
		 * categoryController=(com.code.blog.controller.CategoryController)
		 * configurableApplicationContext.getBean(CategoryController.class);
		 * System.out.println("categoryController "+categoryController.hashCode());
		 * CategoryController
		 * categoryController1=(com.code.blog.controller.CategoryController)
		 * configurableApplicationContext.getBean(CategoryController.class);
		 * System.out.println("categoryController1 "+categoryController1.hashCode());
		 */
		
	}
	@Bean
    public ModelMapper modelMapper() {
	    return new ModelMapper();
	
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("Kirti"));
		
		Role role=new Role();
		role.setId(AppConstants.NORMAL_USER);
		role.setName("ROLE_USER");
		Role role1=new Role();
		role1.setId(AppConstants.ADMIN_USER);
		role1.setName("ROLE_ADMIN");
	   List<Role> roles=List.of(role,role1);
	   List<Role> savedRoles  = roleRepo.saveAll(roles);
	   savedRoles.forEach(System.out::println);
	}
}
