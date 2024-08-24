package com.code.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.blog.payload.ApiResponse;
import com.code.blog.payload.CommentsDto;
import com.code.blog.services.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class CommentController {
     @Autowired
	private CommentService commentService;
	
	@PostMapping("/user/{userId}/post/{postId}/comments")
	public ResponseEntity<CommentsDto> createCategory(@Valid @RequestBody CommentsDto commentsDto,
			@PathVariable Integer userId,
			@PathVariable Integer postId){
		CommentsDto commentsDtoCreated= this.commentService.createComment(commentsDto,postId,userId);
		return new ResponseEntity<CommentsDto>(commentsDtoCreated,HttpStatus.CREATED);
	}
	@DeleteMapping("/comment/{commentId}")
	public ApiResponse deletePostById(@PathVariable Integer commentId) {
		 this.commentService.deleteComment(commentId);
		return new ApiResponse("Post deleted Successfully !!!!!!",true);

	}
}
