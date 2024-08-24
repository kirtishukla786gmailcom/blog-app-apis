package com.code.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.code.blog.entities.Category;
import com.code.blog.entities.Post;
import com.code.blog.entities.User;
import com.code.blog.exceptions.ResourceNotFoundException;
import com.code.blog.payload.PostDto;
import com.code.blog.payload.PostResponse;
import com.code.blog.repositories.CategoryRepo;
import com.code.blog.repositories.PostRepo;
import com.code.blog.repositories.UserRepo;
import com.code.blog.services.PostService;
@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer catId, Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","User Id",userId));
		Category category=this.categoryRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", catId));
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		return this.modelMapper.map(this.postRepo.save(post), PostDto.class);
	}
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post postToUpdate=this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post",  "Post Id", postId));
		postToUpdate.setTitle(postDto.getTitle());
		postToUpdate.setContent(postDto.getContent());
		postToUpdate.setImageName(postDto.getImageName());
		return modelMapper.map(this.postRepo.save(postToUpdate), PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post postToDelete=this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post",  "Post Id", postId));
		this.postRepo.delete(postToDelete);
	}

	@Override
	public PostResponse getAllPosts(Integer pagenumber,Integer pagesize,String sortBy,String sortDir ) {
		Sort sort=sortDir.equalsIgnoreCase("ASC")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable p=PageRequest.of(pagenumber, pagesize,sort);
		Page<Post> pagePost=this.postRepo.findAll(p);
		PostResponse postResponse=new PostResponse();
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setContents(pagePost.getContent().stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList()));
		return postResponse;
	}

	@Override
	public PostDto getPostsById(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow( () -> new ResourceNotFoundException("Post", "Post Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getPostsByCategory(Integer categoryId,Integer pagenumber,Integer pagesize ) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		Pageable p=PageRequest.of(pagenumber, pagesize);
		Page<Post> pagePost= this.postRepo.findByCategory(category,p);
		
		PostResponse postResponse=new PostResponse();
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setContents(pagePost.getContent().stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList()));
		
		return postResponse;
	}

	@Override
	public PostResponse getPostsByUser(Integer userId,Integer pagenumber,Integer pagesize ) {
		User user=this.userRepo.findById(userId).orElseThrow( () -> new ResourceNotFoundException("User", "User Id", userId));
		Pageable p=PageRequest.of(pagenumber, pagesize);
		Page<Post> pagePost=this.postRepo.findByUser(user,p);
		PostResponse postResponse=new PostResponse();
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setContents(pagePost.getContent().stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList()));
		
		return postResponse;
	}

	@Override
	public PostResponse searchPosts(String keyword,Integer pagenumber,Integer pagesize) {
		// TODO Auto-generated method stub
		Pageable page=PageRequest.of(pagenumber, pagesize);
		Page<Post> pagePost=this.postRepo.findByTitleContaining(keyword, page);
		PostResponse postResponse=new PostResponse();
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setContents(pagePost.getContent().stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList()));
		
		return postResponse;
	}

	

}
