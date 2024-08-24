package com.code.blog.services;

import java.util.List;

import com.code.blog.payload.PostDto;
import com.code.blog.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto,Integer catId,Integer userId);
	PostDto updatePost(PostDto postDto,Integer postId);
	void deletePost(Integer postId);
	PostResponse getAllPosts(Integer pagenumber,Integer pagesize,String sortBy,String sortDir );
	PostDto getPostsById(Integer postId);
	PostResponse getPostsByCategory(Integer categoryId,Integer pagenumber,Integer pagesize );
	PostResponse getPostsByUser(Integer userId,Integer pagenumber,Integer pagesize );
	PostResponse searchPosts(String keyword,Integer pagenumber,Integer pagesize);
	
}
