package com.code.blog.services;

import com.code.blog.payload.CommentsDto;

public interface CommentService {
   
	CommentsDto createComment(CommentsDto commentsDto,Integer postID,Integer userId);
	void deleteComment(Integer commentId);
}
