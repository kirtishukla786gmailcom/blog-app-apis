package com.code.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code.blog.entities.Comment;
import com.code.blog.entities.Post;
import com.code.blog.entities.User;
import com.code.blog.exceptions.ResourceNotFoundException;
import com.code.blog.payload.CommentsDto;
import com.code.blog.payload.PostDto;
import com.code.blog.repositories.CommentRepo;
import com.code.blog.repositories.PostRepo;
import com.code.blog.repositories.UserRepo;
import com.code.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public CommentsDto createComment(CommentsDto commentsDto, Integer postID, Integer userId) {
		// TODO Auto-generated method stub
		Post post=this.postRepo.findById(postID).orElseThrow(() -> new ResourceNotFoundException("post", " post ID", postID));
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user ", "user Id",userId));
		Comment comment=modelMapper.map(commentsDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		return modelMapper.map(commentRepo.save(comment), CommentsDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		commentRepo.delete(commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "comment Id", commentId)));
		
	}

}
