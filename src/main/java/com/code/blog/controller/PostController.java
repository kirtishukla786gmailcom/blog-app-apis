package com.code.blog.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.code.blog.config.AppConstants;
import com.code.blog.payload.ApiResponse;
import com.code.blog.payload.PostDto;
import com.code.blog.payload.PostResponse;
import com.code.blog.services.FileService;
import com.code.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value(value = "${project.images}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		PostDto createdPost = this.postService.createPost(postDto, categoryId, userId);
		return new ResponseEntity<>(createdPost, HttpStatus.CREATED);

	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId,
			@RequestParam(value = "pageNumber", defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2",required = false) Integer pageSize) {
		PostResponse posts = this.postService.getPostsByUser(userId,pageNumber,pageSize);
		return new ResponseEntity<>(posts, HttpStatus.OK);

	}
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer catId,
			@RequestParam(value = "pageNumber", defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2",required = false) Integer pageSize) {
		PostResponse posts = this.postService.getPostsByCategory(catId,pageNumber,pageSize);
		return new ResponseEntity<>(posts, HttpStatus.OK);

	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto posts = this.postService.getPostsById(postId);
		return new ResponseEntity<>(posts, HttpStatus.OK);

	}
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			                                        @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			                                        @RequestParam(value = "sortBy", defaultValue =AppConstants.SORT_BY,required = false) String sortBy,
			                                        @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir) {
		PostResponse postResponse = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);

	}
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePostById(@PathVariable Integer postId) {
		 this.postService.deletePost(postId);
		return new ApiResponse("Post deleted Successfully !!!!!!",true);

	}
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto>  updatePostById(@RequestBody PostDto postDto,@PathVariable Integer postId) {
		PostDto postToUpdate = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<>(postToUpdate, HttpStatus.OK);

	}
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<PostResponse> searchPostByTitle(@PathVariable String keyword,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "2", required = false) Integer pageSize) {
		PostResponse postResponse = this.postService.searchPosts(keyword, pageNumber, pageSize);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);

	}
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam(name = "image") MultipartFile  image,
			                                             @PathVariable Integer postId) throws IOException{
		PostDto postDto= this.postService.getPostsById(postId);
		
		String filename=this.fileService.uploadImage(path, image);
		postDto.setImageName(filename);
		return new ResponseEntity<>(this.postService.updatePost(postDto, postId), HttpStatus.OK);
		}
	
	@GetMapping(value="/posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadPostImage(@PathVariable String imageName,HttpServletResponse servletResponse) throws IOException{
		
		InputStream resource=this.fileService.getResource(path, imageName);
		servletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,servletResponse.getOutputStream());
	
	}
	
	}
