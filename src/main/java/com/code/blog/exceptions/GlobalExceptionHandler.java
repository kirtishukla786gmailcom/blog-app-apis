package com.code.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.code.blog.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
		return new ResponseEntity<ApiResponse>(new ApiResponse(resourceNotFoundException.getMessage(),false),HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String ,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
		Map<String,String> resp=new  HashMap<>();	
		methodArgumentNotValidException.getBindingResult().getAllErrors()
		.forEach(e1 -> 
		{
			String fieldname=((FieldError)e1).getField();
			String defmessage=e1.getDefaultMessage();
			resp.put(fieldname, defmessage);
		});
		return new ResponseEntity<Map<String ,String>>(resp,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException apiException){
		return new ResponseEntity<ApiResponse>(new ApiResponse(apiException.getMessage(),true),HttpStatus.BAD_REQUEST);
	}
}
