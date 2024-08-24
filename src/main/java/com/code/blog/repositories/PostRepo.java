package com.code.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.code.blog.entities.Category;
import com.code.blog.entities.Post;
import com.code.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	Page<Post> findByUser(User user,Pageable pageable);
	Page<Post> findByCategory(Category category,Pageable pageable);
	//@Query("select p from posts p where p.title like :key")
//	Page<Post> findByTitleContaining(@Param("key") String keyword,Pageable pageable);
	Page<Post> findByTitleContaining(String keyword,Pageable pageable);

}
